package com.biz.shop.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.shop.domain.AuthorityVO;
import com.biz.shop.domain.UserDetailsVO;
import com.biz.shop.persistence.AuthorityDao;
import com.biz.shop.persistence.DDL_Dao;
import com.biz.shop.persistence.UserDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserDao userDao;
	private final AuthorityDao authDao;
	private final DDL_Dao ddl_Dao;
	
	public UserDetailsServiceImpl(UserDao userDao, AuthorityDao authDao, DDL_Dao ddl_Dao) {
		// 매개변수에 Dao를 빼려면 필드변수에서 final을 빼고 @Autowired 붙여줘도 된다
		this.userDao = userDao;
		this.authDao = authDao;
		this.ddl_Dao = ddl_Dao;
		
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
		
		ddl_Dao.create_table(create_user_table);
		ddl_Dao.create_table(create_auth_table);
	}
	
	// tbl_users 테이블로부터 username, password, enabled 값을 가져와서 UserDetailsVO에 담기
	// loadUserByUsername() method는 AuthenticationProvider에서 호출하여 로그인한 사용자 정보를 가져간다
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsVO userVO = userDao.findByUsername(username);
		if(userVO == null) {
			return null;
		}
		
		userVO.setAuthorities(this.getAuthorities(username));
		
		return userVO;
	}
	
	// UserDetailsVO에 권한 리스트를 집어넣기 위해
	// authorities 테이블에서 ROLE 정보를 가져오는 메소드
	private Collection<GrantedAuthority> getAuthorities(String username) {
		// 1. authorities 테이블에서 username으로 조회한 ROLE list 가져오기
		List<AuthorityVO> authList = authDao.findByUsername(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		// List<AuthorityVO> 리스트를 List<GrantedAuthority>로 변환
		for(AuthorityVO vo : authList) {
			SimpleGrantedAuthority sAuth = new SimpleGrantedAuthority(vo.getAuthority());
			authorities.add(sAuth);
		}
		
		return authorities;
	}
}
