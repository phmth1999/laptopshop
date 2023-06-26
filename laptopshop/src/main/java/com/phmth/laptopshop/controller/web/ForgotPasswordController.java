package com.phmth.laptopshop.controller.web;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.UserDto;
import com.phmth.laptopshop.exception.UserException;
import com.phmth.laptopshop.service.IUserService;
import com.phmth.laptopshop.utils.JavaMail;
import com.phmth.laptopshop.utils.URL;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth/")
public class ForgotPasswordController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	private IUserService userService;
	
	@Autowired
	private JavaMail javaMail;

	@GetMapping("forgot-password")
	public ModelAndView showForgotPasswordForm() {
		return new ModelAndView("/web/forgot-password/index");
	}

	@PostMapping("forgot-password")
	public ModelAndView processForgotPassword(@RequestParam("email") String email, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/web/forgot-password/index");
		
		try {
			String token = UUID.randomUUID().toString();
			long tokenExpireAt = new Date().getTime() + TimeUnit.MINUTES.toMillis(5);
			userService.updateResetPasswordToken(email, token, tokenExpireAt);

			String subject = "Password Reset Authentication";
			
			String resetPasswordLink = URL.getSiteURL(request) + "/auth/reset-password?token=" + token;
			String content = "<p>Hello,</p>" 
							+ "<p>Please click the link below to change your password:</p>" 
							+ "<h3><a href=\"" + resetPasswordLink + "\">Change my password</a></h3>" 
							+ "<p>Note: This link will expire in 5 minutes</p>";
			javaMail.sendEmail(email, subject, content);

			mav.addObject("success", "We have sent a reset password link to your email. Please check.");
		
		} catch (UserException e) {
			mav.addObject("error", e.getMessage());
		} catch (MessagingException e) {
			mav.addObject("error", "Error while sending email");
		}

		return mav;
	}

	@GetMapping("reset-password")
	public ModelAndView showResetPasswordForm(@RequestParam(value = "token") String token) {
		ModelAndView mav = new ModelAndView("/web/forgot-password/reset-password");
		try {
			if (userService.checkResetPasswordToken(token)) {
				mav.addObject("token", token);
			}else {
				mav.addObject("error", "Invalid Token");
			}
		} catch (UserException e) {
			mav.addObject("error", e.getMessage());
		}
		return mav;
	}

	@PostMapping("reset-password")
	public ModelAndView handleResetPassword(
							@RequestParam(value = "token") String token,
							@RequestParam(value = "newPassword") String newPassword) {
		ModelAndView mav = new ModelAndView("/web/forgot-password/reset-password");
		Optional<UserDto> userEntity = userService.findByResetPasswordToken(token);
		if(!userEntity.isEmpty()) {
			userService.updatePassword(userEntity.get().getId(), newPassword);
			mav.addObject("success", "You have successfully changed your password.");
		}else {
			mav.addObject("eror", "You have failed to change your password.");
		}
		return mav;
	}

}
