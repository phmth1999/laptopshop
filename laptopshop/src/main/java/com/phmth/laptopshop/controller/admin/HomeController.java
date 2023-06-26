package com.phmth.laptopshop.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController("adminHome")
@RequestMapping("/admin")
public class HomeController {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping({"/","/home"})
	public ModelAndView showHomePage() {
		return new ModelAndView("admin/home/index");
	}
}
