package com.biz.sec.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biz.sec.domain.UserDetailsVO;

public class AuthProviderImpl implements AuthenticationProvider {
	
	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsSvc;
	
	/**
	 * secutiry-context에 bean으로 등록되어 있는 passwordEncoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * spring security를 커스터마이징할 때
	 * 로그인을 세세히 제어하기 위한 코드를 작성해야 하는 중요한 메소드 
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// spring form:form에서 전송한 메시지는 authentication에 담겨져서 여기로 온다
		
		// authentication으로부터 로그인 폼에서 보낸 username과 password 추출
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		
		// Service -> Dao를 통해서 DB로부터 사용자 정보 가져오기
		UserDetailsVO user = (UserDetailsVO) userDetailsSvc.loadUserByUsername(username);
		if( !passwordEncoder.matches(password, user.getPassword()) ) {
			throw new BadCredentialsException("비밀번호 오류");
		}
		
		// enabled가 false = username이 정지된 아이디일 경우
		if(!user.isEnabled()) {
			throw new BadCredentialsException("사용 정지된 ID 입니다");
		}
		
		// UserDetailsService에서 보내준 사용자 정보를 Controller로 보내기
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
		token.setDetails(user);
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
