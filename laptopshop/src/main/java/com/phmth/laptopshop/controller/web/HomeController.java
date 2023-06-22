package com.phmth.laptopshop.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.service.ICategoryService;

@RestController("webHome")
@RequestMapping({"/","/home"})
public class HomeController {
	
	@Autowired
	private ICategoryService categoryService;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping
	public ModelAndView homePage() {
		
		ModelAndView mav = new ModelAndView("web/home/index");
		
		mav.addObject("laptopCategory", categoryService.findOne((long) 1).get().getProducts().iterator());
		mav.addObject("keyboardCategory", categoryService.findOne((long) 2).get().getProducts().iterator());
		mav.addObject("mouseCategory", categoryService.findOne((long) 3).get().getProducts().iterator());
		
		return mav;
	}
}
