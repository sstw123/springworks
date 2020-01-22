package com.app.money.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.app.money.domain.MoneySearchDTO;
import com.app.money.domain.PaginationDTO;
import com.app.money.domain.ResultServListVO;
import com.app.money.domain.ResultWantedListVO;
import com.app.money.service.ApiCodeListService;
import com.app.money.service.MoneyDetailService;
import com.app.money.service.MoneyListService;
import com.app.money.service.PaginationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes("moneySearchDTO")
@Controller
public class MoneyController {
	
	@Autowired
	ApiCodeListService codeSvc;
	
	@Autowired
	MoneyListService listSvc;
	
	@Autowired
	MoneyDetailService detailSvc;
	
	@Autowired
	PaginationService pagiSvc;
	
	
	// 맨 처음 ModelAttribute를 찾아갈 때 없으면 이쪽으로 찾아온다
	@ModelAttribute("moneySearchDTO")
	public MoneySearchDTO moneySearchDTO() {
		return listSvc.defaultSearchSetting();
	}
	
	
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String search(@ModelAttribute("moneySearchDTO") MoneySearchDTO msDTO, Model model) {
		
		model.addAttribute("MS_DTO", msDTO);// form:form 태그에 보내줄 값
		model.addAttribute("SelectApiMap", codeSvc.selectApiCodeMap());// form:option 태그에 보내줄 값
		
		return "home";
	}
	
	@RequestMapping(value="/link",method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	public String linkList(@ModelAttribute("moneySearchDTO") MoneySearchDTO msDTO, Model model, @RequestParam(value="currPage", required=false, defaultValue="1") long currPage, String dummy) {
		
		
		String lifeArray = listSvc.calcLifeArray(msDTO.getBirth());// 생년월일 기준으로 만 나이 계산하고, 만 나이 기준으로 생애주기 코드 계산
		msDTO.setLifeArray(lifeArray);// msDTO의 lifeArray 세팅
		msDTO.setPageNo(currPage + "");
		
		// model.addAttribute("MS_DTO",msDTO);// 디버그용
		
		ResultWantedListVO resultWantedListVO = listSvc.getWantedList(msDTO);// msDTO 값으로 요청메세지 보내서 API 응답메세지 가져오기
		
		List<ResultServListVO> servList = resultWantedListVO.servList;// API 응답에서 servList 가져오기, link 화면에서 보여줌
		long totalCount = Long.valueOf(resultWantedListVO.totalCount);// API 응답에서 totalCount 가져오기, pagination 계산용도
		
		PaginationDTO pagiDTO = pagiSvc.pagination(totalCount, currPage); // paginationDTO 정보 가져오기
		
		model.addAttribute("pagiDTO", pagiDTO);
		model.addAttribute("SERV_LIST", servList);
		
		return "link";
	}
	
	@RequestMapping(value="/link",method=RequestMethod.GET, produces="application/json; charset=UTF-8")
	public String linkList(@ModelAttribute("moneySearchDTO") MoneySearchDTO msDTO, Model model, @RequestParam(value="currPage", required=false, defaultValue="1") long currPage) {
		
		msDTO.setPageNo(currPage + "");
		ResultWantedListVO resultWantedListVO = listSvc.getWantedList(msDTO);// msDTO 값으로 요청메세지 보내서 API 응답메세지 가져오기
		
		List<ResultServListVO> servList = resultWantedListVO.servList;// API 응답에서 servList 가져오기, link 화면에서 보여줌
		long totalCount = Long.valueOf(resultWantedListVO.totalCount);// API 응답에서 totalCount 가져오기, pagination 계산용도
		
		PaginationDTO pagiDTO = pagiSvc.pagination(totalCount, currPage); // paginationDTO 정보 가져오기
		
		model.addAttribute("pagiDTO", pagiDTO);
		model.addAttribute("SERV_LIST", servList);
		
		return "link";
	}
	
	// 안쓰는 메소드
	/*
	@RequestMapping(value="detail", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
	public String detail(String servId, Model model) {
		
		ResultWantedDetailVO detailVO = detailSvc.getDetailVO(servId);
		
		model.addAttribute("DETAIL_VO", detailVO);
		log.debug("디테일VO : " + detailVO.toString());
		
		return "include/include-detail";
	}
	*/
}
