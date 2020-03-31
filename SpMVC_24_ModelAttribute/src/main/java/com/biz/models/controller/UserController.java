package com.biz.models.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.biz.models.domain.UserVO;
import com.biz.models.service.UserService;

public class UserController {
	
	@Autowired
	UserService userService;
	
	/*
	 * 메소드에 @ModelAttribute를 설정하고 뒤에 value로 객체 이름을 지정해주면
	 * 컨트롤러의 다른 메소드가 실행되기 전에 코드를 실행하여 객체를 model에 담아준다
	 * (단, value에 key값을 꼭 지정해줘야 한다)
	 * 
	 * 컨트롤러에서 view에 rendering을 위한 데이터를 내려보낼 때
	 * 필요할때마다 model을 생성하고 model.addAttribute()를 설정해줘야 하는데
	 * 공통적으로 view에서 사용해야할 데이터가 있을 때 method에 @ModelAttribute()를 설정해두면
	 * view에서 해당 데이터를 가져다가 rendering 용으로 사용 가능하다
	 * 
	 * 상속받아서 사용하면 모든 컨트롤러에서 공통으로 사용할 수 있다는 장점이 있다
	 */
	@ModelAttribute(value = "userVO")
	public UserVO getUser() {
		UserVO userVO = userService.getUser("admin");
		
		return userVO;
	}

}
