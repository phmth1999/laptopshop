package com.phmth.laptopshop.security.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

	private OAuth2User oauth2User;
	
	private long id;

	public CustomOAuth2User(OAuth2User oauth2User) {
		this.oauth2User = oauth2User;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oauth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 List<GrantedAuthority> authorities = new ArrayList<>();
	        oauth2User.getAuthorities().forEach(ga -> authorities.add(ga));
	        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

	        return authorities;
	}

	@Override
	public String getName() {
		return oauth2User.getAttribute("name");
	}

	public String getEmail() {
		return oauth2User.<String>getAttribute("email");
	}
	
	public String getImg() {
		return oauth2User.<String>getAttribute("picture");
	}
	
}
