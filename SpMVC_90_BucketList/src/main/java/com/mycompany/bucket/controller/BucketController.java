package com.mycompany.bucket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.bucket.model.BucketDTO;
import com.mycompany.bucket.service.BucketService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BucketController {
	
	@Autowired
	BucketService bucketSvc;

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String list(Model model) {
		
		List<BucketDTO> bucketList = bucketSvc.selectAll();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		return "home";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@ModelAttribute BucketDTO bucketDTO, Model model) {
		bucketSvc.save(bucketDTO);
		
		List<BucketDTO> bucketList = bucketSvc.selectAll();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		return "bucket_list";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(int b_id, Model model) {
		bucketSvc.delete(b_id);
		
		List<BucketDTO> bucketList = bucketSvc.selectAll();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		return "bucket_list";
	}
	
	@RequestMapping(value="/sc_update", method=RequestMethod.POST)
	public String scUpdate(int b_id, Model model) {
		BucketDTO bucketDTO = bucketSvc.findById(b_id);
		bucketSvc.scUpdate(bucketDTO);
		
		List<BucketDTO> bucketList = bucketSvc.selectAll();
		model.addAttribute("BUCKET_LIST", bucketList);
		
		return "bucket_list";
	}

}
