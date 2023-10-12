package com.phmth.laptopshop.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phmth.laptopshop.service.ICookieService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService implements ICookieService {

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@Override
	public Map<String, String> getAll() {

		// create map list
		Map<String, String> mapCookies = new HashMap<>();

		// create cookies list
		Cookie[] cookies = request.getCookies();

		// case have cookie
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				mapCookies.put(cookie.getName(), cookie.getValue());
			}
		}

		return mapCookies;

	}

	@Override
	public String getValue(String name) {

		// create cookies list
		Cookie[] cookies = request.getCookies();

		// case have cookie
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// case the correct cookie to look for
				if (cookie.getName().equalsIgnoreCase(name)) {
					return cookie.getValue();
				}
			}
		}

		return null;

	}

	@Override
	public Cookie add(String name, String value, int hours) {

		// create new cookie
		Cookie cookie = new Cookie(name, value);
		// set time life
		cookie.setMaxAge(hours * 60 * 60);
		// set access scope
		cookie.setPath("/");
		cookie.setHttpOnly(true);

		response.addCookie(cookie);

		return cookie;

	}

	@Override
	public Cookie remove(String name) {

		// create cookies list
		Cookie[] cookies = request.getCookies();

		// case have cookie
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// case the correct cookie to look for
				if (cookie.getName().equalsIgnoreCase(name)) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					cookie.setHttpOnly(true);
					response.addCookie(cookie);
					return cookie;
				}
			}
		}

		return null;
	}

}
