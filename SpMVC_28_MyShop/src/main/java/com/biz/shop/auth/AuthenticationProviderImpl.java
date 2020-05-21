package com.biz.shop.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.biz.shop.domain.UserDetailsVO;

import lombok.RequiredArgsConstructor;

// @Controller, @Service 등과 같이 자동으로 scan되어 bean으로 등록한 클래스 지정
// Controller, Service, Repository 등과 같이 명시적으로 지정하기 애매한 클래스는 Component로 지정한다

// Spring security에서 login을 직접 수행하는 클래스
// login을 customizing하여 UserDetailsService로부터 DB를 조회하여 사용자 정보를 받아오보고
// 비밀번호를 BCryptPasswordEncoder를 사용해서 암호화 된 DB의 값과 비교하교
// 일치하면 로그인을 승인하는 역할을 수행한다
@Component
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {
	
	// @Autowired를 대체하는 방식으로
	// 객체를 private final로 선언하고
	// @RequiredArgsConstructor를 선언하여 사용할 수 있도록 초기화 수행
	private final UserDetailsService userService;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		UserDetailsVO userVO = (UserDetailsVO) userService.loadUserByUsername(username);
		if(userVO == null) {
			throw new UsernameNotFoundException("등록되지 않은 사용자입니다.");
		}
		
		if( !passwordEncoder.matches(password, userVO.getPassword()) ) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}
		
		if(!userVO.isEnabled()) {
			throw new InsufficientAuthenticationException("활성화되지 않은 계정입니다.");
		}
		
		return new UsernamePasswordAuthenticationToken(userVO, null, userVO.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
