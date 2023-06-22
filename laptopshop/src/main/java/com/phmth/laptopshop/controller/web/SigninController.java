package com.phmth.laptopshop.controller.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth/")
public class SigninController {

	@GetMapping("sign-in")
	public ModelAndView signInPage() {

		ModelAndView mav = new ModelAndView("/web/sign-in/index");

		return mav;

	}
}
