package com.biz.iolist.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.iolist.domain.IolistDTO;
import com.biz.iolist.domain.IolistVO;
import com.biz.iolist.service.IolistService;

@RequestMapping(value="/iolist")
@Controller
public class IolistController {
	
	@Autowired
	IolistService ioService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String ioHome(Model model) {
		
		/*
		 * 아직 viewFindAll() method를 생성하지 않은 상태에서 viewfindAll) 호출하여 할일을 먼저 정의한 것
		 * TDD(Test Driven Developer0
		 */
		List<IolistVO> iolistList = ioService.viewFindAll();
		
		/*
		 * jsp view에서 사용할 변수 역할을 수행할 Attribute를 등록하는 절차이고
		 * 이 때, 이름 "IO_LIST"를 대문자로 사용하는 이유는 대소문자가 섞이면 문자 차이로 인한 오류가 발생하거나
		 * 값을 표시하지 못하는 일이 있는데 이를 방지하기 위함이다
		 */
		model.addAttribute("IO_LIST", iolistList);
		return "iolist/iolist-list";
	}
	
	/*
	 * POST에서 데이터를 수신할 때 DTO에 데이터를 받을 경우
	 * DTO의 필드변수 중에 String이 아닌 데이터가 있을 경우
	 * 입력 form에서 해당 값을 입력하지 않고 전송하면 400 오류가 발생한다
	 * Type.valueOf() method에서 exception이 발생하기 때문이다
	 */
	// 새로등록 버튼을 클릭하면 입력 페이지를 보여주는 method
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String list(String id, Model model) {
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		
		String curDate = sd.format(date);
		IolistDTO ioDTO;
		
		if(id != null) {
			ioDTO = ioService.findById(id);
			if(ioDTO != null) {
				ioDTO.setIo_date(curDate);
			}
		} else {
			ioDTO = IolistDTO.builder()
					.io_seq(0)
					.io_date(curDate)
					.io_price(0)
					.io_qty(1000)
					.io_total(0)
					.build();
		}
		
		model.addAttribute("IO_DTO", ioDTO);
		
		return "iolist/iolist-input";
	}
	
	// 입력 페이지 form에서 데이터를 보냈을 때 수신할 method
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String list(IolistDTO ioDTO, Model model) {
		int ret = ioService.insert(ioDTO);
		
		return "redirect:/iolist/insert?id=" + ioDTO.getIo_seq();
	}
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(String id, Model model) {
		long io_seq = Long.valueOf(id);
		IolistDTO ioDTO = ioService.findBySeq(io_seq);
		return "iolist/iolist-view";
	}

}
