package com.phmth.laptopshop.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.FormSignup;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.exception.UserException;
import com.phmth.laptopshop.service.IUserService;
import com.phmth.laptopshop.utils.URL;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth/")
public class SignupController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("sign-up")
	public ModelAndView signUpPage() {
		
		ModelAndView mav = new ModelAndView("/web/sign-up/index");
		mav.addObject("formSignup", new FormSignup());
		
		return mav;
	}
	
	@PostMapping("sign-up")
	public ModelAndView signUp(
					@ModelAttribute("formSignup") FormSignup formSignup, 
					HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/web/sign-up/index");
		try {
			UserEntity userEntity = userService.register(formSignup);
			if(userEntity != null) {
				String verifyLink = URL.getSiteURL(request) + "/auth/sign-up/verify?token="+userEntity.getRegisterToken();
				sendEmail(userEntity.getEmail(), verifyLink);
				mav.addObject("success", "You have signed up successfully! Please check your email to verify your account.");
			}
		} catch (UserException e) {
			mav.addObject("error", e.getMessage());
		} catch (MessagingException e) {
			mav.addObject("error", "Error while sending email");
		}
		
		return mav;
	}
	
	public void sendEmail(String email, String link) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setTo(email);
		
		String subject = "Account Verification";
		helper.setSubject(subject);
		
		String content = "<p>Hello,</p>" 
				+ "<p>Please click the link below to verify your registration:</p>"
				+ "<h3><a href='"+link+"'>VERIFY</a></h3>" 
				+ "<p>Note: This link will expire in 5 minutes</p>";
		helper.setText(content, true);
		
		mailSender.send(message);
}
	
	@GetMapping("/sign-up/verify")
	public ModelAndView verify(@RequestParam("token") String token) {
		ModelAndView mav = new ModelAndView("/web/sign-up/index");
		if(userService.checkRegisterToken(token)) {
			mav.addObject("success", "Congratulations, your account has been verified. Please sign in.");
		}else {
			mav.addObject("error", "Sorry, we could not verify account. It maybe already verified, or verification code is incorrect.");
		}
		
		return mav;
	}

}
