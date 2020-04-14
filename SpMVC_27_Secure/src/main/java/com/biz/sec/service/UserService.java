package com.biz.sec.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.domain.UserVO;
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
	
	public UserService(PasswordEncoder pwEncoder, UserDao userDao) {
		super();
		this.pwEncoder = pwEncoder;
		this.userDao = userDao;
		
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
	public int insert(String username, String password) {
		
		// 회원가입 form에서 전달받은 password 값을 암호화 시키는 과정
		String encPW = pwEncoder.encode(password);
		log.debug("암호화된 비밀번호 : " + encPW);
		
		UserDetailsVO userVO = UserDetailsVO.builder()
							.username(username)
							.password(encPW)
							.build();
		
		int ret = userDao.insert(userVO);
		
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

	public int update(UserDetailsVO userVO) {
		return userDao.update(userVO);
	}

}
