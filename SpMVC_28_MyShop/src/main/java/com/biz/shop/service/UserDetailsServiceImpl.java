package com.biz.shop.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.biz.shop.persistence.UserDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserDao userDao;
	public UserDetailsServiceImpl(UserDao userDao) {
		// 매개변수에 Dao를 빼려면 필드변수에서 final을 빼고 @Autowired 붙여줘도 된다
		this.userDao = userDao;
		// 테이블 생성 부분
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDao.findByUsername(username);
	}
	
	
}
