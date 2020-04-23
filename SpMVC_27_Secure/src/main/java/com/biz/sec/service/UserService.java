package com.biz.sec.service;

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

import com.biz.sec.domain.AuthorityVO;
import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.persistence.AuthoritiesDao;
import com.biz.sec.persistence.UserDao;
import com.biz.sec.utils.PbeEncryptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	//private final로 선언하면
	//@Autowired 또는 @RequiredArgsConstructor를 이용해 bean에 설정된 id와 객체이름을 똑같이 설정하여 bean을 불러올 수 있다
	//@Autowired
	private final PasswordEncoder bcryptEncoder;
	private final UserDao userDao;
	private final AuthoritiesDao authDao;
	private final MailSendService mailSvc;
	
	public UserService(PasswordEncoder bcryptEncoder, UserDao userDao, AuthoritiesDao authDao, MailSendService mailSvc) {
		super();
		this.bcryptEncoder = bcryptEncoder;
		this.userDao = userDao;
		this.authDao = authDao;
		this.mailSvc = mailSvc;
		
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
	 * @since 2020-04-10
	 * Map 타입의 VO 데이터를 UserVO 타입으로 변경
	 */
	
	// @Transactional
	// 여러 쿼리를 한번에 실행할 때 하나라도 오류가 나면
	// 여러 쿼리가 들어있는 메소드 실행 전 상태로 롤백
	@Transactional
	public int insert(String username, String password) {
		
		// 회원가입 form에서 전달받은 password 값을 암호화 시키는 과정
		String encPW = bcryptEncoder.encode(password);
		
		UserDetailsVO userVO = UserDetailsVO.builder()
							.username(username)
							.password(encPW)
							.build();
		
		int ret = userDao.insert(userVO);
		
		List<AuthorityVO> authList = new ArrayList<>();
		authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("ROLE_USER").build());
		authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("USER").build());
		authDao.insert(authList);
		
		return ret;
	}
	
	@Transactional
	public UserDetailsVO findByUserName(String username) {
		return userDao.findByUserName(username);
	}
	
	@Transactional
	public UserDetailsVO findByUsernameAuthorities(String username) {
		return userDao.findByUsernameAuthorities(username);
	}
	
	public UserDetailsVO findById(long id) {
		return userDao.findById(id);
	}
	
	@Transactional
	public boolean pw_check(String password) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String loginPW = userDao.findByUserName(username).getPassword();
		// 현재 로그인한 사용자의 암호화된 비밀번호와 입력된 password와 비교
		boolean ret = bcryptEncoder.matches(password, loginPW);
		
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
	
	@Transactional
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
		userVO.setPassword(bcryptEncoder.encode(password));
		
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
	
	/**
	 * @since 2020-04-20
	 * @author me
	 * 새로운 회원가입 메소드(userVO로 가입, 기존은 아이디와 패스워드로 가입)
	 * email 인증을 거쳐 가입할 것이므로 userVO를 파라미터로 받아서 enabled를 false로 만들고
	 * role 정보는 업데이트하지 않는다
	 * 이후 이메일 인증이 오면 enabled와 role 정보를 설정한다
	 * @param userVO
	 * @return
	 */
	// 두 개 이상의 쿼리 => @Transactional
	@Transactional
	public int insert(UserDetailsVO userVO) {
		// 이메일 인증 전, 활성화 되지 않은 사용자로 세팅하기
		userVO.setEnabled(false);
		userVO.setAuthorities(null);
		
		String encPW = bcryptEncoder.encode(userVO.getPassword());
		userVO.setPassword(encPW);
		
		try {
			mailSvc.join_send(userVO);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int ret = userDao.insert(userVO);
		return ret;
	}
	
	@Transactional
	public boolean email_valid(String username, String email) {
		String plainUsername = PbeEncryptor.decrypt(username);
		UserDetailsVO userVO = userDao.findByUserName(plainUsername);
		
		String plainEmail = PbeEncryptor.decrypt(username);
		if(plainEmail.equalsIgnoreCase(userVO.getEmail())) {
			userVO.setEnabled(true);
			userDao.updateInfo(userVO);
			
			List<AuthorityVO> authList = new ArrayList<>();
			authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("ROLE_USER").build());
			authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("USER").build());
			authDao.insert(authList);
			
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * @since 2020-04-21
	 * 회원정보를 받아서 DB에 저장하고 회원정보를 활성화할 수 있도록 하기 위해
	 * 인증정보를 생성한 후 Controller로 return하는 메소드
	 * @param userVO
	 * @return
	 */
	public String insert_getToken(UserDetailsVO userVO) {
		// UUID : 989bbfdd-ed54-430c-a20a-c348614e84be
		// - 로 나누어진 부분중 처음 부분을 전부 대문자로 만들기
		String email_token = UUID.randomUUID().toString().split("-")[0].toUpperCase();
		
		//Email 보내기
		mailSvc.email_auth(userVO, email_token);
		
		// UUID 암호화
		String enc_email_token = PbeEncryptor.encrypt(email_token);
		return enc_email_token;
	}
	
	@Transactional
	public boolean email_token_insert(UserDetailsVO userVO, String secret_key, String secret_value) {
		// secret_key : email 인증키를 암호화한 값을 복호화한 값
		// secret_value : 이메일 인증키
		// 두개를 대조해여 boolean값 리턴
		boolean bKey = PbeEncryptor.decrypt(secret_key).equalsIgnoreCase(secret_value);
		if(bKey) {
			// 정확한 인증키를 입력했다면
			userVO.setEnabled(true);// 계정 활성화
			// bean에 설정한 Spring Secure의 bcryptEncoder로 암호화한 뒤 DB 저장
			userVO.setPassword(bcryptEncoder.encode(userVO.getPassword()));
			userDao.insert(userVO);
			
			// 권한 DB insert
			List<AuthorityVO> authList = new ArrayList<>();
			authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("ROLE_USER").build());
			authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("USER").build());
			authDao.insert(authList);
		}
		return bKey;
	}

	@Transactional
	public boolean email_token_update(String username, String secret_key, String secret_value) {
		// secret_key : email 인증키를 암호화한 값을 복호화한 값
		// secret_value : 이메일 인증키
		// 두개를 대조해여 boolean값 리턴
		boolean bKey = PbeEncryptor.decrypt(secret_key).equalsIgnoreCase(secret_value);
		if(bKey) {
			// 정확한 인증키를 입력했다면
			String strUsername = PbeEncryptor.decrypt(username);// 암호화 된 id 복호화
			UserDetailsVO userVO = userDao.findByUserName(strUsername);// 아이디로 DB에서 VO 가져오기
			
			userVO.setEnabled(true);// 계정 활성화
			userDao.updateInfo(userVO);// 활성화된 유저정보 업데이트
			
			// 기존 권한 삭제 후 새로운 권한 insert
			authDao.delete(userVO.getUsername());			
			List<AuthorityVO> authList = new ArrayList<>();
			authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("ROLE_USER").build());
			authList.add(AuthorityVO.builder().username(userVO.getUsername()).authority("USER").build());
			authDao.insert(authList);
		}
		
		return bKey;
	}

	public List<UserDetailsVO> findId(UserDetailsVO userVO) {
		List<UserDetailsVO> userList = null;
		userList = userDao.findByEmail(userVO.getEmail());
		
		return userList;
	}
	
	public byte findPW(UserDetailsVO userVO) {
		UserDetailsVO dbUserVO = userDao.findByUserName(userVO.getUsername());
		byte res = 4;
		if(dbUserVO == null) {
			// DB에 아이디가 없으면 3 리턴
			res = 3;
		} else {
		
			if(dbUserVO.getEmail().equals(userVO.getEmail()) ) {
				// db에 저장된 username의 email과 form에서 입력한 email이 같으면 비밀번호 재설정 메일 보내기
				boolean bRes = false;
				try {
					bRes = mailSvc.email_setPW(userVO.getUsername());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// 성공적으로 보냈으면 1 리턴, 메일 보내기 실패시 2 리턴
				res = (byte) (bRes ? 1 : 2);
			}
		}
		
		// db에 저장된 username의 email과 form에서 입력한 email이 다르면 4 리턴
		return res;
	}

	public int setPW(UserDetailsVO userVO) {
		// userVO에는 username, password만 담겨있다
		// 패스워드 암호화
		String encPW = PbeEncryptor.encrypt(userVO.getPassword());
		userVO.setPassword(encPW);
		// DB에 새로운 비밀번호 저장
		return userDao.updatePW(userVO);
	}

}
