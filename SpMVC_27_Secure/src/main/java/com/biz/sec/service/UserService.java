package com.biz.sec.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.sec.domain.AuthorityVO;
import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.persistence.AuthoritiesDao;
import com.biz.sec.persistence.UserDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	//private final로 선언하면
	//@Autowired 또는 @RequiredArgsConstructor를 이용해 bean에 설정된 id와 객체이름을 똑같이 설정하여 bean을 불러올 수 있다
	//@Autowired
	private final PasswordEncoder pwEncoder;
	private final UserDao userDao;
	private final AuthoritiesDao authDao;
	
	public UserService(PasswordEncoder pwEncoder, UserDao userDao, AuthoritiesDao authDao) {
		super();
		this.pwEncoder = pwEncoder;
		this.userDao = userDao;
		this.authDao = authDao;
		
		String create_user_table = "CREATE TABLE IF NOT EXISTS tbl_users ( " + 
				" id BIGINT PRIMARY KEY AUTO_INCREMENT, " + 
				" user_name VARCHAR(50) UNIQUE, " + 
				" user_pass VARCHAR(125), " + 
				" enabled BOOLEAN default true, " +
				" email VARCHAR(50), " +
				" phone VARCHAR(20), " +
				" address VARCHAR(125) " +
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

	/**
	 * @since 2020-04-09
	 * @author 나
	 * 
	 * @param username
	 * @param password
	 * @return
	 * 
	 * 회원가입 시 비밀번호를 암호화한 뒤 DB에 insert하기
	 * 
	 * @update 2020-04-10
	 * Map<String, String> 타입의 VO 데이터를
	 * UserVO 타입으로 변경
	 */
	
	// @Transactional
	// 여러 쿼리를 한번에 실행할 때 하나라도 오류가 나면
	// 여러 쿼리가 들어있는 메소드 실행 전 상태로 롤백
	@Transactional
	public int insert(String username, String password) {
		
		// 회원가입 form에서 전달받은 password 값을 암호화 시키는 과정
		String encPW = pwEncoder.encode(password);
		
		UserDetailsVO userVO = UserDetailsVO.builder()
							.username(username)
							.password(encPW)
							.build();
		
		int ret = userDao.insert(userVO);
		
		List<AuthorityVO> authList = new ArrayList();
		authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("ROLE_USER").build());
		authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("USER").build());
		authDao.insert(authList);
		
		return ret;
	}

	public UserDetailsVO findByUserName(String username) {
		return userDao.findByUserName(username);
	}
	
	public UserDetailsVO findByUsernameAuthorities(String username) {
		return userDao.findByUsernameAuthorities(username);
	}

	public UserDetailsVO findById(long id) {
		return userDao.findById(id);
	}
	
	public boolean pw_check(String password) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String loginPW = userDao.findByUserName(username).getPassword();
		// 현재 로그인한 사용자의 암호화된 비밀번호와 입력된 password와 비교
		boolean ret = pwEncoder.matches(password, loginPW);
		
		return ret;
	}
	
	// 관리자가 다른 유저 정보 수정하기
	@Transactional
	public int updateInfo(UserDetailsVO userVO, String[] authList) {
		UserDetailsVO dbUserVO = userDao.findByUserName(userVO.getUsername());
		
		dbUserVO.setEnabled(userVO.isEnabled());
		dbUserVO.setEmail(userVO.getEmail());
		dbUserVO.setPhone(userVO.getPhone());
		dbUserVO.setAddress(userVO.getAddress());
		
		int ret = userDao.updateInfo(dbUserVO);
		
		if(ret > 0) {
			List<AuthorityVO> authCollection = new ArrayList<AuthorityVO>();
			for(String auth : authList) {
				//input에서 받은 auth 값이 비어있으면(""면) 무시함 
				if(!auth.isEmpty()) {
					AuthorityVO authVO = AuthorityVO.builder()
										.username(dbUserVO.getUsername())
										.authority(auth)
										.build();
					
					authCollection.add(authVO);
				}
			}
			authDao.delete(dbUserVO.getUsername());
			authDao.insert(authCollection);
		}
		
		return ret;
	}
	
	// 로그인 한 유저가 자기 정보 수정하기
	@Transactional
	public int updateInfo(UserDetailsVO userVO) {
		Authentication oldAuth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsVO loginUserVO = (UserDetailsVO) oldAuth.getPrincipal();
		
		loginUserVO.setEmail(userVO.getEmail());
		loginUserVO.setPhone(userVO.getPhone());
		loginUserVO.setAddress(userVO.getAddress());
		
		int ret = userDao.updateInfo(loginUserVO);
		if(ret > 0) {
			// 업데이트 성공시, 전역으로 쓰는 SecurityContextHolder에 새로운 Authentication 업데이트 시켜주기
			Authentication newAuth = new UsernamePasswordAuthenticationToken(loginUserVO, oldAuth.getCredentials(), oldAuth.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}
		
		return ret;
	}
	
	public int pw_change(String password) {
		// 패스워드에 아무것도 입력하지 않았다면 아무 일도 하지 않음
		if(password.isEmpty()) {
			return 0;
		}
		// 현재 사용자 이름 가져오기
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		// DB에서 사용자 이름으로 VO 정보 가져오기
		UserDetailsVO userVO = userDao.findByUserName(username);
		// userVO에 비밀번호 암호화해서 저장하기
		userVO.setPassword(pwEncoder.encode(password));
		
		// DB에 username 기준으로 정보 update하기
		return this.updatePW(userVO);
	}
	
	public int updatePW(UserDetailsVO userVO) {
		return userDao.updatePW(userVO);
	}
	
	private Collection<GrantedAuthority> getAuthoritiesCS(String[] authList) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(String auth : authList) {
			if(!auth.isEmpty()) {
				SimpleGrantedAuthority sAuth = new SimpleGrantedAuthority(auth);
				authorities.add(sAuth);
			}
		}
		
		return authorities;
	}
	
	// selectAll() 메소드는 2개의 테이블을 select하고 있다
	// 이런 경우 1번 테이블의 데이터는 가져왔는데 2번 테이블을 가져오기 전
	// 다른 사용자가 2번 테이블의 데이터를 수정할 수 있다
	// @Transactional로 여기에 트랜잭션을 걸어주면 셀렉트할 때 다른 사용자가 select중인 테이블의 정보를 수정하지 못하도록 한다
	@Transactional
	public List<UserDetailsVO> selectAll() {
		return userDao.selectAll();
	}

}
