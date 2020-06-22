package com.biz.shop.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationProvider authProvider;
	
	// ROLE에 따라 접근 권한을 설정함
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//super.configure(auth);
		auth.authenticationProvider(authProvider);
	}
	
	// security 필터를 거치지 않고 요청에 응답할 요소들
	@Override
	public void configure(WebSecurity web) throws Exception {
		//super.configure(web);
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		
		// Security 한글 인코딩 설정
		// Security에서 POST 전송 시 한글 깨짐 방지
		http.addFilterBefore(filter, CsrfFilter.class);
		
		http.authorizeRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/mypage").hasAnyRole("ADMIN", "USER")
		.antMatchers("/user/**").permitAll()
		.antMatchers("/**").permitAll();
		//.anyRequest().authenticated(); // 위에 나열한 것 외에는 모두 인증 필요 없음. ("/**").permitAll()과 같다.
		
		http.formLogin()
		.loginProcessingUrl("/login")// security에서 지원하는 login URL
		.loginPage("/user/login")// login form
		.usernameParameter("username")
		.passwordParameter("password");
		
		http.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/");
	}
	
	// Spring Security의 단방향 암호화 클래스
	// DB에 저장된 암호화 된 비밀번호와, 입력받은 평문 비밀번호 비교하기
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
