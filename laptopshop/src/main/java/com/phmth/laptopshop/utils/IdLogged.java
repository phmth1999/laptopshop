package com.phmth.laptopshop.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.phmth.laptopshop.security.oauth2.CustomOAuth2User;
import com.phmth.laptopshop.security.user.CustomUser;

public class IdLogged {
	
	public static long getId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long id = 0;
		if (principal instanceof CustomUser) {
			id = ((CustomUser) principal).getId();
		} else if (principal instanceof CustomOAuth2User) {
			id = ((CustomOAuth2User) principal).getId();
		}
		return id;
	}
}
