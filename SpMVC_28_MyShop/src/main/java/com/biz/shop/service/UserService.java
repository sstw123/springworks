package com.biz.shop.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.shop.dao.AuthoritiesDao;
import com.biz.shop.dao.UserDao;
import com.biz.shop.model.AuthorityVO;
import com.biz.shop.model.UserDetailsVO;
import com.biz.util.PbeEncryptor;

@Service
public class UserService {
	
	// bean으로 설정된 PasswordEncoder 가져오기
	private final PasswordEncoder bcryptEncoder;
	private final UserDao userDao;
	private final AuthoritiesDao authDao;
	private final MailSendService mailSvc;
	
	// DB 스키마에 테이블 없으면 새로 생성하기
	public UserService(PasswordEncoder bcryptEncoder, UserDao userDao, AuthoritiesDao authDao, MailSendService mailSvc) {
		this.bcryptEncoder = bcryptEncoder;
		this.userDao = userDao;
		this.authDao = authDao;
		this.mailSvc = mailSvc;
		
		String create_user_table = "CREATE TABLE IF NOT EXISTS tbl_users ( " + 
				" id BIGINT PRIMARY KEY AUTO_INCREMENT, " + 
				" username VARCHAR(50) UNIQUE, " + 
				" password VARCHAR(125), " + 
				" enabled BOOLEAN default true, " +
				" accountNonExpired BOOLEAN default true, " +
				" accountNonLocked BOOLEAN default true, " +
				" credentialsNonExpired BOOLEAN default true, " +
				" email VARCHAR(50), " +
				" phone VARCHAR(20), " +
				" age INT " +
				" ) "
				;
		
		String create_auth_table = "CREATE TABLE IF NOT EXISTS authorities ( " + 
				" id BIGINT PRIMARY KEY AUTO_INCREMENT, " + 
				" username VARCHAR(50), " + 
				" authority VARCHAR(50) " + 
				" ) "
				;
		
		userDao.create_table(create_user_table);
		userDao.create_table(create_auth_table);
	}
	
	// selectAll() 메소드는 2개의 테이블을 select하고 있다
	// 이런 경우 1번 테이블의 데이터는 가져왔는데 2번 테이블을 가져오기 전
	// 다른 사용자가 2번 테이블의 데이터를 수정할 수 있다
	// @Transactional로 여기에 트랜잭션을 걸어주면 셀렉트할 때 다른 사용자가 select중인 테이블의 정보를 수정하지 못하도록 한다
	@Transactional
	public List<UserDetailsVO> selectAll() {
		return userDao.selectAll();
	}
	
	@Transactional
	public UserDetailsVO findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	@Transactional
	public UserDetailsVO findByUsernameFromAuthorities(String username) {
		return userDao.findByUsernameFromAuthorities(username);
	}
	
	public UserDetailsVO findById(long id) {
		return userDao.findById(id);
	}
	
	// 로그인 한 유저가 자기 정보 수정하기
	@Transactional
	public int updateInfo(UserDetailsVO userVO) {
		Authentication oldAuth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsVO loginUserVO = (UserDetailsVO) oldAuth.getPrincipal();
		
		loginUserVO.setEmail(userVO.getEmail());
		loginUserVO.setPhone(userVO.getPhone());
		loginUserVO.setAge(userVO.getAge());
		
		int ret = userDao.updateInfo(loginUserVO);
		if(ret > 0) {
			// 업데이트 성공시, 전역으로 쓰는 SecurityContextHolder에 새로운 Authentication 업데이트 시켜주기
			Authentication newAuth = new UsernamePasswordAuthenticationToken(loginUserVO, oldAuth.getCredentials(), oldAuth.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}
		
		return ret;
	}
	
	// 관리자가 다른 유저 정보 수정하기
	// 유저 정보, 설정한 권한 따로 받기
	@Transactional
	public int updateInfo(UserDetailsVO userVO, String[] arrAuth) {
		UserDetailsVO dbUserVO = userDao.findByUsername(userVO.getUsername());
		
		dbUserVO.setEnabled(userVO.isEnabled());
		dbUserVO.setEmail(userVO.getEmail());
		dbUserVO.setPhone(userVO.getPhone());
		dbUserVO.setAge(userVO.getAge());
		
		int ret = userDao.updateInfo(dbUserVO);
		
		if(ret > 0) {
			List<AuthorityVO> authList = new ArrayList<AuthorityVO>();
			for(String auth : arrAuth) {
				//input에서 받은 auth 값이 비어있으면(="") 무시함 
				if(!auth.isEmpty()) {
					AuthorityVO authVO = AuthorityVO.builder()
										.username(dbUserVO.getUsername())
										.authority(auth)
										.build();
					
					authList.add(authVO);
				}
			}
			authDao.delete(dbUserVO.getUsername());
			authDao.insert(authList);
		}
		return ret;
	}
	
	@Transactional
	public boolean pw_check(String password) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String loginPW = userDao.findByUsername(username).getPassword();
		// 현재 로그인한 사용자의 암호화된 DB 비밀번호와 입력된 password와 비교
		boolean ret = bcryptEncoder.matches(password, loginPW);
		
		return ret;
	}
	
	@Transactional
	public int pw_change(String password, String re_password) {
		// 서버에서 유효성 검사
		// 1. 비밀번호, 비밀번호 확인에 아무것도 입력하지 않은 경우
		// 2. 비밀번호와 비밀번호 확인이 서로 다른 경우 아무 것도 하지 않음
		if(password.isEmpty() || re_password.isEmpty() || !password.equals(re_password)) {
			return 0;
		}
		
		// 유효성 검사 통과시 비밀번호 변경
		// 현재 사용자 이름 가져오기
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		// DB에서 사용자 이름으로 VO 가져오기
		UserDetailsVO userVO = userDao.findByUsername(username);
		// 새로운 비밀번호 암호화하기
		userVO.setPassword(bcryptEncoder.encode(password));
		// DB에 username 기준으로 암호화 된 비밀번호 update하기
		return userDao.updatePW(userVO);
	}

	public List<UserDetailsVO> findId(UserDetailsVO userVO) {
		List<UserDetailsVO> userList = null;
		userList = userDao.findByEmail(userVO.getEmail());
		
		return userList;
	}
	
	public byte findPW(UserDetailsVO userVO) {
		UserDetailsVO dbUserVO = userDao.findByUsername(userVO.getUsername());
		byte res = 0;
		if(dbUserVO == null) {
			// DB에 아이디가 없으면 1 리턴
			res = 1;
		} else {
			// DB에 아이디가 있는 경우
			if(dbUserVO.getEmail().equals(userVO.getEmail()) ) {
				// db에 저장된 username의 email과 form에서 입력한 email이 같으면
				// 비밀번호 재설정 메일 보내기
				
				boolean bRes = false;
				try {
					bRes = mailSvc.send_new_pw_link(userVO.getUsername());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// 성공적으로 보냈으면 3 리턴, 메일 보내기 실패시 4 리턴
				res = (byte) (bRes ? 3 : 4);
			} else {
				// db에 저장된 username의 email과 form에서 입력한 email이 다르면 2 리턴
				res = 2;
			}
		}
		
		return res;
	}

	public int new_pw(UserDetailsVO userVO, String re_password) {
		// userVO에는 암호화된 username, password만 담겨있다
		// 서버에서 유효성 검사
		// 1. 비밀번호, 비밀번호 확인에 아무것도 입력하지 않은 경우
		// 2. 비밀번호와 비밀번호 확인이 서로 다른 경우 아무 것도 하지 않음
		if(userVO.getPassword().isEmpty() || re_password.isEmpty() || !userVO.getPassword().equals(re_password)) {
			return 0;
		}
		
		// username 복호화
		userVO.setUsername(PbeEncryptor.decrypt(userVO.getUsername()));
		// 패스워드 암호화
		userVO.setPassword(PbeEncryptor.encrypt(userVO.getPassword()));
		// DB에 새로운 비밀번호 저장
		return userDao.updatePW(userVO);
	}

}
