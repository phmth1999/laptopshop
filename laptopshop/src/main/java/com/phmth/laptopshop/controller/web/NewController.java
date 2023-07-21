package com.phmth.laptopshop.controller.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.NewDto;
import com.phmth.laptopshop.service.INewService;

@RestController
@RequestMapping("/new")
public class NewController {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NewController.class);
	
	@Autowired
	private INewService newService;
	
	@GetMapping
	public ModelAndView showNew(@RequestParam(name = "page", defaultValue = "1") int page) {
		ModelAndView mav = new ModelAndView("/web/new/index");
		int limit = 4;
		
		Page<NewDto> listPageNew = newService.findAll(page, limit);
		List<NewDto> listNew = listPageNew.getContent();
		
		mav.addObject("news", listNew);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageNew.getTotalPages());
		return mav;
	}
	@PostMapping
	public ModelAndView processNew(@RequestParam(name = "page", defaultValue = "1") int page) {
		ModelAndView mav = new ModelAndView("/web/new/index");
		int limit = 4;
		logger.error(""+page);
		Page<NewDto> listPageNew = newService.findAll(page, limit);
		List<NewDto> listNew = listPageNew.getContent();
		
		mav.addObject("news", listNew);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageNew.getTotalPages());
		return mav;
	}
	
	@PostMapping("/detail")
	public ModelAndView contactDetail(@RequestParam("id") long id, @RequestParam(name = "page", defaultValue = "1") int page) {
		ModelAndView mav = new ModelAndView("/web/new/detail");
		mav.addObject("currentPage", page);
		mav.addObject("newItem", newService.findById(id).get());
		return mav;
	}
}
