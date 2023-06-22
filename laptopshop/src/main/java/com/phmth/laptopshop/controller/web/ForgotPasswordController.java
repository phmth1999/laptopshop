package com.phmth.laptopshop.controller.web;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.exception.UserException;
import com.phmth.laptopshop.service.IUserService;
import com.phmth.laptopshop.utils.URL;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth/")
public class ForgotPasswordController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private IUserService userService;

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

			String resetPasswordLink = URL.getSiteURL(request) + "/auth/reset-password?token=" + token;
			sendEmail(email, resetPasswordLink);

			mav.addObject("success", "We have sent a reset password link to your email. Please check.");
		
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
			
			String subject = "Password Reset Authentication";
			helper.setSubject(subject);
			
			String content = "<p>Hello,</p>" 
					+ "<p>Please click the link below to change your password:</p>" 
					+ "<h3><a href=\"" + link + "\">Change my password</a></h3>" 
					+ "<p>Note: This link will expire in 5 minutes</p>";
			helper.setText(content, true);
			
			mailSender.send(message);
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
		Optional<UserEntity> userEntity = userService.findByResetPasswordToken(token);
		if(!userEntity.isEmpty()) {
			userService.updatePassword(userEntity.get(), newPassword);
			mav.addObject("success", "You have successfully changed your password.");
		}else {
			mav.addObject("eror", "You have failed to change your password.");
		}
		return mav;
	}

}
