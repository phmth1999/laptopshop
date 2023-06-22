package com.phmth.laptopshop.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/contact")
public class ContactController {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@GetMapping
	public ModelAndView contactPage() {
		return new ModelAndView("/web/contact/index");
	}
	
}
