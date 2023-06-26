package com.phmth.laptopshop.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.service.IProductService;

@RestController("webHome")
@RequestMapping({"/","/home"})
public class HomeController {
	
	@Autowired
	private IProductService productService;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping
	public ModelAndView homePage() {
		
		ModelAndView mav = new ModelAndView("web/home/index");
		
		mav.addObject("laptopCategory", productService.findByCategoryId(1));
		mav.addObject("keyboardCategory", productService.findByCategoryId(2));
		mav.addObject("mouseCategory", productService.findByCategoryId(3));
		
		return mav;
	}
}
