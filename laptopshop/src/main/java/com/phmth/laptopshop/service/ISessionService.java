package com.phmth.laptopshop.service;

public interface ISessionService {

	public <T> T get(String name);

	public void set(String name, Object value);

	public void remove(String name);
}
