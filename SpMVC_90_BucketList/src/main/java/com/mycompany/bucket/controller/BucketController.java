package com.mycompany.bucket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.bucket.model.BucketDTO;
import com.mycompany.bucket.model.PaginationDTO;
import com.mycompany.bucket.service.BucketService;
import com.mycompany.bucket.service.PaginationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BucketController {
	
	@Autowired
	BucketService bucketSvc;
	
	@Autowired
	PaginationService pageSvc;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String list(Model model, @RequestParam(value="currPage",required=false,defaultValue="1") int currPage) {
		
		//this.selectAllByPage(model, currPage);
		List<BucketDTO> bucketList = bucketSvc.selectAll();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		if(bucketSvc.countAll() == 0) {
			model.addAttribute("EMPTY_LIST", true);
		}
		
		return "home";
	}
	
	@RequestMapping(value="/sc_all", method=RequestMethod.POST)
	public String scAll(Model model) {
		
		//this.selectAllByPage(model, 1);
		List<BucketDTO> bucketList = bucketSvc.selectAll();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		if(bucketSvc.countAll() == 0) {
			model.addAttribute("EMPTY_LIST", true);
		}
		
		return "include/bucket_table";
	}
	
	@RequestMapping(value="/sc_true", method=RequestMethod.POST)
	public String scTrue(Model model) {
		
		//this.selectAllByPage(model, 1);
		List<BucketDTO> bucketList = bucketSvc.selectSuccessTrue();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		if(bucketSvc.countAll() == 0) {
			model.addAttribute("EMPTY_LIST", true);
		}
		
		return "include/bucket_table";
	}
	
	@RequestMapping(value="/sc_false", method=RequestMethod.POST)
	public String scFalse(Model model) {
		
		//this.selectAllByPage(model, 1);
		List<BucketDTO> bucketList = bucketSvc.selectSuccessFalse();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		if(bucketSvc.countAll() == 0) {
			model.addAttribute("EMPTY_LIST", true);
		}
		
		return "include/bucket_table";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@ModelAttribute BucketDTO bucketDTO, Model model) {
		bucketSvc.save(bucketDTO);
		
		//this.selectAllByPage(model, 1);
		List<BucketDTO> bucketList = bucketSvc.selectAll();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		return "include/bucket_table";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(int b_id, Model model) {
		bucketSvc.delete(b_id);
		
		//this.selectAllByPage(model, 1);
		List<BucketDTO> bucketList = bucketSvc.selectAll();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		if(bucketSvc.countAll() == 0) {
			model.addAttribute("EMPTY_LIST", true);
		}
		
		return "include/bucket_table";
	}
	
	@RequestMapping(value="/sc_update", method=RequestMethod.POST)
	public String scUpdate(int b_id, Model model) {
		bucketSvc.scUpdate(b_id);
		
		//this.selectAllByPage(model, 1);
		List<BucketDTO> bucketList = bucketSvc.selectAll();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		return "include/bucket_table";
	}
	
	private void selectAllByPage(Model model, int currPage) {
		long totalCount = bucketSvc.countAll();
		PaginationDTO pageDTO = pageSvc.makePageInfo(totalCount, currPage);
		model.addAttribute("PAGE_DTO", pageDTO);
		
		List<BucketDTO> bucketList = bucketSvc.selectAllByPage(pageDTO);
		model.addAttribute("BUCKET_LIST", bucketList);
	}

}
