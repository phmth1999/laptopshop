package com.phmth.laptopshop.utils;

import jakarta.servlet.http.HttpServletRequest;

public class URL {
	public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
