package com.phmth.laptopshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.phmth.laptopshop", exclude = {SecurityAutoConfiguration.class })

public class LaptopshopApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(LaptopshopApplication.class, args);
	}

}
