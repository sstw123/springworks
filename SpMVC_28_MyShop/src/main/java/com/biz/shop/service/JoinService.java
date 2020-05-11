package com.biz.shop.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.shop.dao.AuthoritiesDao;
import com.biz.shop.dao.UserDao;
import com.biz.shop.model.AuthorityVO;
import com.biz.shop.model.UserDetailsVO;
import com.biz.util.PbeEncryptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JoinService {
	
	private final UserDao userDao;
	private final AuthoritiesDao authDao;
	private final MailSendService mailSvc;
	private final UserService userSvc;
	private final PasswordEncoder bcryptEncoder;
	
	/**
	 * @since 2020-04-20
	 * @author me
	 * email 인증이 되지 않은 상태로 DB에 저장할 것이므로 userVO를 파라미터로 받아서 비밀번호 암호화 후 저장
	 * authorities 테이블엔 ROLE_UNAUTH로 저장
	 * 이후 이메일 인증 링크를 클릭하면 role 정보를 ROLE_USER로 설정한다
	 * @param userVO
	 * @return
	 */
	// 두 개 이상의 쿼리 => @Transactional
	@Transactional
	public int insert(UserDetailsVO userVO) {
		// 이미 DB에 있는 아이디면 아무 일도 하지 않음
		if(userSvc.findByUsername(userVO.getUsername()) != null) {
			return 0;
		}

		// 메일 보내기 실패 시 아무 일도 하지 않음
		try {
			mailSvc.send_join_auth_link(userVO);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 이메일 인증 전, 활성화 되지 않은 사용자로 세팅하기
		userVO.setEnabled(true);
		// 비밀번호 암호화하기
		String encPW = bcryptEncoder.encode(userVO.getPassword());
		userVO.setPassword(encPW);
		
		int ret = userDao.insert(userVO);
		
		List<AuthorityVO> authList = new ArrayList<>();
		authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("ROLE_UNAUTH").build());
		authDao.insert(authList);
		
		return ret;
	}
	
	@Transactional
	public boolean email_link_auth(String username, String email) {
		// 아이디, 이메일 복호화
		String plainUsername = PbeEncryptor.decrypt(username);
		String plainEmail = PbeEncryptor.decrypt(email);
		
		UserDetailsVO userVO = userDao.findByUsername(plainUsername);
		
		// DB에 username이 저장되어있지 않으면 아무 일도 하지 않음
		if(userVO == null) return false;
		
		// 링크의 이메일 복호화 값이 DB의 이메일 값과 같으면 권한 업데이트
		if(plainEmail.equalsIgnoreCase(userVO.getEmail())) {
			authDao.delete(userVO.getUsername());
			List<AuthorityVO> authList = new ArrayList<>();
			authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("ROLE_USER").build());
			authDao.insert(authList);
			return true;
		} else {
			return false;
		}
	}

}
