package com.biz.memo.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.memo.domain.HobbyListDTO;
import com.biz.memo.domain.UserDTO;
import com.biz.memo.domain.UserHobbyDTO;
import com.biz.memo.persistence.UserDao;

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
		/*
		 * 최초로 등록되는 사용자는 관리자이다
		 * u_grade 값을 "A"로 세팅, 아닐시 값을 "U"로 세팅
		 * 
		 * tbl_user 테이블에 데이터가 하나도 없으면 회원정보 DTO의 u_grade 필드 변수에 문자열 A, 아니면 U를 저장한 뒤 insert 수행
		 */
		String u_grade = (userDao.userCount() > 0) ? "U" : "A";
		userDTO.setU_grade(u_grade);
		
		int ret = userDao.insertUser(userDTO);
		
		String[] arrHobby = userDTO.getU_hobby();
		for(String s : arrHobby) {
			UserHobbyDTO uhDTO = UserHobbyDTO.builder()
					.uh_id(userDTO.getU_id())
					.uh_code(s).build();
			
			userDao.insertUHobby(uhDTO);
		}
		
		return 0;
	}

	@Override
	public boolean userIdCheck(String u_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserDTO userLoginCheck(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int userUpdate(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void userOut(UserDTO userDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<HobbyListDTO> getHobbyList() {
		// TODO Auto-generated method stub
		return userDao.selectAllHobby();
	}
	
	

}
