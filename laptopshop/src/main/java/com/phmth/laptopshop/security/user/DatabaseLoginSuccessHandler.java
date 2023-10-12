package com.phmth.laptopshop.security.user;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(
						HttpServletRequest request, 
						HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
		
		Set<String> roles = new HashSet<String>();
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			roles.add(authority.getAuthority());
		}
		
		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/laptopshop/admin/home");
		} else if (roles.contains("ROLE_USER")) {
			response.sendRedirect("/laptopshop/home");
		}
	}

}
