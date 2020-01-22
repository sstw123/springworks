package com.biz.memo.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.memo.domain.UserDTO;
import com.biz.memo.persistence.UserDao;

/*
 * interface를 imp하여 생성한 클래스에 @Service를 붙여주면
 * @Autowired를 수행할 때 Interface 객체 형식으로 코드를 작성하면
 * 자동으로 해당 객체를 가져와서 객체를 초기화 해준다
 */
@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	SqlSession sqlSession;
	
	UserDao userDao;
	
	@Autowired
	public void userDaoMapper() {
		userDao = sqlSession.getMapper(UserDao.class);
	}

	@Override
	public int userJoin(UserDTO userDTO) {
		// TODO Auto-generated method stub
		
		// 테이블에 유저 정보가 있다면 U, 아니면 A
		if(userDao.userCount() > 0) {
			userDTO.setU_grade("U");
		} else {
			userDTO.setU_grade("A");
		}
		
		return userDao.userInsert(userDTO);
	}

	@Override
	public boolean userIdCheck(String u_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userLoginCheck(UserDTO userDTO) {
		
		String inputU_id = userDTO.getU_id();
		String inputU_pw = userDTO.getU_pw();
		
		UserDTO userDB_DTO = userDao.selectById(inputU_id);
		
		// 회원 정보가 DB에 없을 경우
		if(userDB_DTO == null) {
			return false;
		}
		
		String dbU_id = userDB_DTO.getU_id();
		String dbU_pw = userDB_DTO.getU_pw();
		
		// 회원 id는 DB에 존재하지만 pw가 틀렸을 경우
		if (inputU_id.equalsIgnoreCase(dbU_id) && inputU_pw.equals(dbU_pw)) {
			/*
			 * java method에서 객체를 매개변수로 받은 후
			 * 각 필드변수들을 개별적으로 변경하면 파라메터로 주입한 원본의 변수값들이 변경된다
			 * 
			 * 하지만
			 * 객체 = 다른객체
			 * 또는 객체 = new 클래스() 형식으로 주소값을 변경하면
			 * 파라메터로 주입한 원본은 변경되지 않는다
			 * ex) userDTO = userDB_DTO;
			 * 
			 * 그래서 setter를 이용해 변경하려면
			 * userDTO.setU_grade(userDB_DTO.getU_grade());
			 * 처럼 일일히 전부 바꿔주어야 한다
			 * 
			 * 이러한 방식은 번거롭기 때문에 컨트롤러에서 설정해주자
			 */
			
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public UserDTO getUser(String u_id) {
		return userDao.selectById(u_id);
	}

}
