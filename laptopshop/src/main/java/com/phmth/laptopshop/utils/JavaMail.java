package com.phmth.laptopshop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class JavaMail {
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String email, String subject, String content) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);
	}
}
