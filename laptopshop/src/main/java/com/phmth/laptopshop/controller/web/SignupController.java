package com.phmth.laptopshop.controller.web;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.UserDto;
import com.phmth.laptopshop.dto.request.SignupRequest;
import com.phmth.laptopshop.exception.UserException;
import com.phmth.laptopshop.service.IUserService;
import com.phmth.laptopshop.utils.JavaMail;
import com.phmth.laptopshop.utils.URL;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth/")
public class SignupController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private JavaMail javaMail;
	
	@GetMapping("sign-up")
	public ModelAndView signUpPage() {
		
		ModelAndView mav = new ModelAndView("/web/sign-up/index");
		mav.addObject("formSignup", new SignupRequest());
		
		return mav;
	}
	
	@PostMapping("sign-up")
	public ModelAndView signUp(
					@ModelAttribute("formSignup") SignupRequest formSignup, 
					HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/web/sign-up/index");
		try {
			String token = UUID.randomUUID().toString();
			long tokenExpireAt = new Date().getTime() + TimeUnit.MINUTES.toMillis(5);
			UserDto userEntity = userService.register(formSignup, token, tokenExpireAt);
			if(userEntity != null) {
				String subject = "Account Verification";
				
				String verifyLink = URL.getSiteURL(request) + "/auth/sign-up/verify?token="+token;
				String content = "<p>Hello,</p>" 
								+ "<p>Please click the link below to verify your registration:</p>"
								+ "<h3><a href='" + verifyLink + "'>VERIFY</a></h3>" 
								+ "<p>Note: This link will expire in 5 minutes</p>";
				
				javaMail.sendEmail(userEntity.getEmail(), subject, content);
				
				mav.addObject("success", "You have signed up successfully! Please check your email to verify your account.");
			}
		} catch (UserException e) {
			mav.addObject("error", e.getMessage());
		} catch (MessagingException e) {
			mav.addObject("error", "Error while sending email");
		}
		
		return mav;
	}
	
	@GetMapping("/sign-up/verify")
	public ModelAndView verify(@RequestParam("token") String token) {
		ModelAndView mav = new ModelAndView("/web/sign-up/index");
		
		try {
			boolean valid = userService.checkRegisterToken(token);
			
			if(valid) {
				mav.addObject("success", "Congratulations, your account has been verified. Please sign in.");
			}else {
				mav.addObject("formSignup", new SignupRequest());
				mav.addObject("error", "Sorry, we could not verify account. It maybe already verified, or verification code is incorrect.");
			}
		} catch (UserException e) {
			mav.addObject("error", e.getMessage());
		}
		
		return mav;
	}

}
