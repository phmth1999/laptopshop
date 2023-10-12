package com.phmth.laptopshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phmth.laptopshop.service.ISessionService;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService implements ISessionService {

	@Autowired
	HttpSession session;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String name) {

		// case have session
		if (session.getAttribute(name) != null) {

			return (T) session.getAttribute(name);

		}

		// case have not session
		else {

			return null;

		}

	}

	@Override
	public void set(String name, Object value) {

		// set value into session created
		session.setAttribute(name, value);

	}

	@Override
	public void remove(String name) {

		// remove session by name
		session.removeAttribute(name);

	}

}
