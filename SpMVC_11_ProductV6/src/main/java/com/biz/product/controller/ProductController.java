package com.biz.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.product.domain.PageDTO;
import com.biz.product.domain.ProductDTO;
import com.biz.product.domain.ProductFileDTO;
import com.biz.product.service.FileService;
import com.biz.product.service.PageService;
import com.biz.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProductController {

	@Autowired
	ProductService productSvc;
	
	@Autowired
	FileService fSvc;
	
	@Autowired
	PageService pageSvc;
	
	/*
	 * RequestMapping의 produces에서 contentType을 text/json으로 바꾸면
	 * 서비스에서 가져온 DTO에 들어있는 값을 자동으로 json형으로 바꿔서 response 하려고 한다
	 * 
	 * 하지만 자바에는 기본적으로 json형으로 바꿔주는 클래스가 없기 때문에
	 * Jackson Databind를 받아서 이용하기로 한다
	 */
	// @ResponseBody
	@RequestMapping(value="plist", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public String getAllList(@RequestParam(value="search",
			required = false,defaultValue = "") 
			String p_name, @RequestParam(value="currentPageNo", required = false, defaultValue = "1") int currentPageNo, Model model) {
		
		log.debug(String.format("CurrentPageNo : %d",currentPageNo));
		log.debug(String.format("Search : %s",p_name));
		
		long totalCount = productSvc.totalCount(p_name);
		PageDTO pageDTO = pageSvc.getPagination(totalCount, currentPageNo);
		List<ProductDTO> proList = productSvc.selectByNameAndPagination(p_name,pageDTO);
		
		
		// addAttribute에서 이름을 지정하지 않으면 객체의 변수명과 상관없이
		// 객체의 클래스 이름의 맨 앞이 소문자로 바뀐 것과 같은 문자열로 자동 지정된다
		model.addAttribute("pageDTO", pageDTO);
		model.addAttribute("PLIST", proList);
		model.addAttribute("url","plist");
		model.addAttribute("search",p_name);
		
		return "home";
	}
	
	@RequestMapping(value="getFiles",method=RequestMethod.GET)
	public String getFiles(String p_code,Model model) {
		
		List<ProductFileDTO> fileList = fSvc.getFiles(p_code);
		model.addAttribute("files",fileList);
		
		return "files";
	}
	
	/*
	 * 파일 하나를 업로드 할때는 MultipartFile로 업로드를 수행하면 되고
	 * 2개 이상의 파일을 업로드 할 때는 MultipartRequest 객체로 파일을 받아서 처리를 수행
	 */
	@RequestMapping(value="input", method=RequestMethod.POST)
	public String input(@ModelAttribute ProductDTO proDTO,
						@RequestParam("u_file") MultipartFile u_file,
						MultipartHttpServletRequest u_files) {
						// 다수의 정보를 받는 경우 @RequestParam 붙이면 400 오류 발생
		
		log.debug("멀티파일 개수 : " + u_files.getFile("u_files").getSize());
		
		// 파일리스트만 추출
		List<MultipartFile> mfList = u_files.getFiles("u_files");
		
		// getFiles 안의 이름은 input 태그의 name 속성의 값과 같아야 한다
		for(MultipartFile f : u_files.getFiles("u_files")) {
			log.debug("파일이름 : " + f.getOriginalFilename());
		}
		
		// 대표이미지(u_file)가 업로드 되었을 때
		try {
			String profileImage = fSvc.fileUp(u_file);
			if(profileImage != null) {
				if(proDTO.getP_file() != null) {
					fSvc.fileDelete(proDTO.getP_file());
				}
				proDTO.setP_file(profileImage);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("대표이미지 업로드 오류 : " + e.getMessage());
		}
		
		
		// 파일 업로드가 이상없이 완료되면(try-catch) ProductFileDTO형 리스트를 반환받음
		List<ProductFileDTO> upFileList = fSvc.filesUp(u_files);

		
		int ret = 0;
		if(proDTO.getP_code().isEmpty()) {
			log.debug("새로운 상품등록");
			// insert 실행
			// 상품 정보와 ProductFileDTO형 리스트를 insert() 메소드에 전달
			ret = productSvc.insert(proDTO, upFileList);
			
		} else {
			log.debug("기존 상품변경");
			// update 실행
			// 변경할 상품정보와 파일리스트를 update() 메소드에 전달
			ret = productSvc.update(proDTO, upFileList);
		}
		
		
//		log.debug("상품정보입력 : " + proDTO.toString());
//		log.debug("업로드한 파일 : " + u_file.getOriginalFilename());
//		
//		// 파일을 선택하지 않았으면
//		// 파일 업로드에 관련된 코드를 실행하지 않음
//		// 업로드할 파일을 선택했을 때만 파일에 관련된 코드 실행
//		if(!u_file.isEmpty()) {
//			
//			/*
//			 * update를 수행할 때 이미 업로드된 파일이 있으면 기존의 파일을 삭제하고
//			 * 새로운 파일을 업로드 해야하므로 p_file 변수를 검사하여
//			 * 값이 있으면 파일을 삭제
//			 */
//			if(proDTO.getP_file() != null) {
//				fSvc.fileDelete(proDTO.getP_file());
//			}
//			
//			/*
//			 * web에서 파일이 전송되어 오면 fSvc.fileUp() 메소드에게 파일을 어딘가에 저장해달라고 요청
//			 * 정상적으로 파일저장이 완료되면 파일명을 DTO의 p_file 변수에 저장한다 
//			 */
//			String upFileName = fSvc.fileUp(u_file);
//			if(upFileName != null) {
//				proDTO.setP_file(upFileName);
//			}
//		}
		
		return "redirect:/plist";
	}
	
	// 대표이미지 삭제
	@RequestMapping(value="productImgDelete", method=RequestMethod.GET)
	public String imgDelete(String p_code) {
		
		productSvc.proImgDelete(p_code);
		
		return "redirect:/plist";
	}
	
	// 서브이미지 삭제
	@RequestMapping(value="subImgDelete", method=RequestMethod.GET)
	public String subImgDelete(String file_seq) {
		
		productSvc.subImgDelete(file_seq);
		
		return "redirect:/plist";
	}
	
	
	/*
	 * @ResponseBody
	 * 결과물을 view(*.jsp)로 변환하지 않고
	 * 문자열 그대로 또는 객체(vo,dto)를 json 형태로 변환하여
	 * 클라이언트에게 response를 수행하는 기능
	 */
	
	/*
	@ResponseBody
	@RequestMapping(value="getProduct", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public ProductDTO getProduct(String p_code) {
		
		ProductDTO proDTO = pService.selectByPCode(p_code);
		
		return proDTO;
	}
	*/
	
	@ResponseBody
	@RequestMapping(value="getString", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
	// @ResponseBody를 사용할 때는 produces를 설정하는 것이 좋다
	// 특히 한글 데이터를 response 할 때는 반드시 charset=UTF-8로 설정해주자
	public String getString(@RequestParam(value="str", // query 문자열
										required=false, // 혹시 값을 보내지 않더라도 오류가 나지 않도록 함
										defaultValue="없음") // 값을 보내지 않으면 없음 이라는 문자열로 세팅
															// required=false와 defaultValue가 없으면 서버는 client에게 400오류를 보내고 처리를 거부한다
															// 또한 VO나 DTO 등 객체에는 적용하면 안된다
											String myStr) {
		
		if(myStr.equals("없음")) {
			return "http://localhost:8080/product/getString?str=문자열 형식으로 보내세요";
		} else {
			return "보낸 문자열 : " + myStr;
		}
	}
	
	@ResponseBody
	@RequestMapping(value="plist/name", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public List<ProductDTO> getPNames(String p_name) {
		
		List<ProductDTO> proList = productSvc.selectByPName(p_name);
		
		return proList;
	}
	
	/*
	 * produces의 content-Type
	 * 서버에서 문자열, 객체 등등의 실제 데이터를 response 할 때
	 * 어떤 type으로 보낼 것인가를 나타내는 문자열
	 */
	@ResponseBody
	@RequestMapping(value="pname", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getPName(String p_code) {
		
		ProductDTO proDTO = productSvc.selectByPCode(p_code);
		
		//return proDTO.getP_name();
		return "<h1>" + proDTO.getP_name() + "</h1>";
	}
	
	@ResponseBody
	@RequestMapping(value="poprice", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getOPrice(String p_code) {
		
		ProductDTO proDTO = productSvc.selectByPCode(p_code);
		
		return proDTO.getP_oprice() + "";
	}
	
	@ResponseBody
	@RequestMapping(value="piprice", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getIPrice(String p_code) {
		
		ProductDTO proDTO = productSvc.selectByPCode(p_code);
		
		return proDTO.getP_iprice() + "";
	}
	
}
