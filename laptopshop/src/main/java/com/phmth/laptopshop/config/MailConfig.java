package com.phmth.laptopshop.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.phmth.laptopshop.utils.AES;

@Configuration
public class MailConfig {
	@Value("${mailServer.host}")
	private String host;

	@Value("${mailServer.port}")
	private Integer port;

	@Value("${mailServer.username}")
	private String username;

	@Value("${mailServer.password}")
	private String password;

	@Value("${mailServer.isSSL}")
	private String isSSL;

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);

		mailSender.setUsername(AES.decrypt(username, "username"));
		mailSender.setPassword(AES.decrypt(password, "password"));
		mailSender.setDefaultEncoding("UTF-8");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.enable", isSSL);
		props.put("mail.smtp.from", AES.decrypt(username, "username"));
		props.put("mail.debug", "true");

		return mailSender;
	}
}
