package com.phmth.laptopshop.service;

import java.util.Map;

import jakarta.servlet.http.Cookie;

public interface ICookieService {

	public Map<String, String> getAll();

	public String getValue(String name);

	public Cookie add(String name, String value, int hours);

	public Cookie remove(String name);
}
