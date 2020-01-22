package com.biz.gallery.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.gallery.domain.ImageFileVO;
import com.biz.gallery.domain.ImageVO;
import com.biz.gallery.domain.MemberVO;
import com.biz.gallery.service.ImageFileService;
import com.biz.gallery.service.ImageServiceV3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes({"imageVO"})
@RequestMapping("image")
@Controller
public class ImgController {
	
	ImageServiceV3 imageSvc;// Interface이므로 @Qualifier() 사용 불가, 클래스는 사용 가능
	ImageFileService ifSvc;
	
	@Autowired
	public ImgController(ImageServiceV3 imageSvc, ImageFileService ifSvc) {
		this.imageSvc = imageSvc;
		this.ifSvc = ifSvc;
	}
	
	@ModelAttribute("imageVO")
	public ImageVO imageVO() {
		return new ImageVO();
	}
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(Model model) {
		List<ImageVO> imageList = imageSvc.selectAll();
		model.addAttribute("imageList", imageList);
		
		return "home";
	}
	
	@RequestMapping(value="upload", method=RequestMethod.GET)
	public String upload(@ModelAttribute("imageVO") ImageVO imageVO, Model model, HttpSession httpSession) {
		/*
		// 로그인 검사
		MemberVO member = (MemberVO) httpSession.getAttribute("MEMBER");
		if(member == null) {
			model.addAttribute("MODAL", "LOGIN");
			return "home";
		}
		*/
		
		log.debug("=================이미지 업로드 시작=================");
		
		imageVO = new ImageVO();
		model.addAttribute("BODY", "UPLOAD");
		model.addAttribute("imageVO", imageVO);
		
		return "home";
	}
	
	@RequestMapping(value="upload", method=RequestMethod.POST)
	public String upload(@ModelAttribute("imageVO") ImageVO imageVO, SessionStatus sessionStatus) {
		
		imageSvc.insert(imageVO);
//		for(String f : imageVO.getImg_files()) {
//			log.debug("파일이름 : " + f);
//		}
		log.debug("업로드 : " + imageVO.toString());
		sessionStatus.setComplete();
		return "redirect:/image/list";
	}
	
	/*
	 * RequestParam : ?변수=값 으로 전송하고 변수에서 받기
	 * PathVariable : path/값 형식으로 전송하고 변수에서 받기
	 */
	@RequestMapping(value="update/{img_seq}", method=RequestMethod.GET)
	public String update(@PathVariable("img_seq") String img_seq, Model model, HttpSession httpSession) {
		/*
		// 로그인이 되어있는지 아닌지만 검사하기 위해 Object형으로 session 객체 추출
		Object memberOB = httpSession.getAttribute("MEMBER");
		
		// 로그인이 되어있지 않은 경우
		if(memberOB == null) {
			model.addAttribute("MODAL", "LOGIN");
			return "home";
		}
		*/
		
		ImageVO imageVO = imageSvc.selectBySeq(img_seq);
		
		log.debug("수정 : " + imageVO.toString());
		
		model.addAttribute("BODY", "UPLOAD");
		model.addAttribute("imageVO", imageVO);
		
		return "home";
	}
	
	
	@RequestMapping(value="update/{img_seq}", method=RequestMethod.POST)
	public String update(@ModelAttribute("imageVO") ImageVO imageVO, SessionStatus sessionStatus) {
		log.debug("IMAGE VO : " + imageVO.getImg_files());
		// 이미지 이름이 기존의 이미지와 다르면 기존 이미지를 삭제(서비스에 해당 기능 구현)
		int ret = imageSvc.update(imageVO);
		
		/*
		 * SessionAttributes를 사용할 때 객체가 서버 메모리에 남아서 계속 유지되는 상태이므로
		 * insert, update 등을 수행할 때 그 정보를 계속 사용해서 form에 값이 나타나게 된다
		 * 그것을 방지하기 위해 insert나 update가 완료된 후에는
		 * 반드시 SessionStatus의 setComplete() method를 호출해서 초기화 시켜주어야 한다
		 */
		sessionStatus.setComplete();
		
		return "redirect:/image/list";
	}
	
	@RequestMapping(value="/delete/{img_seq}", method=RequestMethod.GET)
	public String delete(@PathVariable("img_seq") String img_seq, Model model, HttpSession httpSession) {
		// 로그인이 되어있는지 아닌지만 검사하기 위해 Object형으로 session 객체 추출
		Object memberOB = httpSession.getAttribute("MEMBER");
		
		// 로그인이 되어있지 않은 경우
		if(memberOB == null) {
			model.addAttribute("MODAL", "LOGIN");
			return "home";
		}

		int ret = imageSvc.delete(img_seq);
		
		return "redirect:/image/list";
	}
	
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("img_seq") String img_seq, String dummy, Model model, HttpSession httpSession) {
		
		Object memberOB = httpSession.getAttribute("MEMBER");
		
		// 로그인이 되어있지 않은 경우
		if(memberOB == null) {
			model.addAttribute("MODAL", "LOGIN");
			return "home";
		}
		
		int ret = imageSvc.delete(img_seq);
		
		// return "redirect:/image/list";
		return ret + "";
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteFile", method=RequestMethod.POST)
	public String deleteFile(@RequestParam("img_file_seq") String img_file_seq) {
		
		int ret = ifSvc.deleteFile(img_file_seq);
		
		return ret + "";
	}
	
	// MultipartHttpServletRequest : 다중 파일 수신하기
	// 업로드 수행 후 파일리스트를 img_upload_list view와 결합하여
	// DB에 저장하기 전 보여주기
	@RequestMapping(value="files_up", method=RequestMethod.POST)
	public String files_up(MultipartHttpServletRequest mFiles, Model model) {
		
		List<ImageFileVO> fileNames = imageSvc.files_up(mFiles);
		
		model.addAttribute("imgList", fileNames);
		
		return "include/img_upload_list";
	}
}
