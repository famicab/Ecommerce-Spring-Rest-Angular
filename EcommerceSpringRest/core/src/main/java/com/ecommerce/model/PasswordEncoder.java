package com.ecommerce.model;

public interface PasswordEncoder {

	boolean matches(String rawPassword, String encodedPassword);
	
	String encode(String rawPassword);
}
