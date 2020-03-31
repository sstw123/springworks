package com.biz.models.service;

import org.springframework.stereotype.Service;

import com.biz.models.dao.UserDao;
import com.biz.models.domain.UserVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserDao userDao;
	
	/*
	 * TDD (Test Driven Development : 테스트 주도 개발)
	 * 1. 클래스에 필요한 메소드의 이름만 만들고
	 * 2. 필요에 따라 매개변수와 리턴 값만 설정한다
	 * 3. Test에서 매개변수를 주입했을 때 리턴되는 값이 일치하는지 테스트하는 코드를 작성한 후
	 * 4. 클래스에서 가상의 일치하는 데이터를 만들고 데이터를 return 하도록 코드 작성
	 * 5. Test를 통과하도록 코드를 리팩토링하여 임시로 클래스를 완성한다
	 * 
	 * Dao에서 데이털르 가져온 후 ID에 따라 사용자 ID와 사용자 이름이 일치하는지 테스트하는 코드를 작성해 둔 상태이다
	 * 1. Dao에 데이터를 저장할 때 테스트 코드를 통과할 수 있는 데이터를 INSERT한 후
	 * 2. 다시 한번 테스트를 수행하여 Dao와 연동되는 것을 계속해서 확인한다
	 */
	
	public UserVO getUser(String userId) {
		/*
		UserVO userVO = UserVO.builder()
				.userId("admin")
				.password("1234")
				.userName("홍길동")
				.userRole("admin")
				.build();
		*/
		UserVO userVO = userDao.findByUserId("admin");
		
		if(userId.equalsIgnoreCase("admin")) {
			
		} else if(userId.equalsIgnoreCase("guest")) {
			/*
			userVO.setUserId("guest");
			userVO.setUserName("성춘향");
			*/
			userVO = userDao.findByUserId("guest");
		} else if(userId.equalsIgnoreCase("dba")) {
			/*
			userVO.setUserId("dba");
			userVO.setUserName("이몽룡");
			*/
			userVO = userDao.findByUserId("dba");
		} else {
			userVO = null;
		}
		
		return userVO;
	}
	

	// insert 메소드는 UsersVO에 담긴 데이터를 받아서 insert를 수행한 후 결과를 return 하도록 되어있다
	// 이 때 MyBatis Dao에서 insert에 성공했다면 0보다 큰 값을 리턴한다(INSERT: 1, UPDATE: 업데이트 된 행의 갯수, DELETE: DELETE된 행의 갯수)
	// insert 테스트를 수행하기 위해 가상으로 1을 리턴하는 메소드를 작성한다
	public int insert(UserVO userVO) {
		return userDao.insert(userVO);
	}


	public int delete(String string) {
		return userDao.delete(string);
	}

}
