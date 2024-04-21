package com.ecommerce.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecommerce.model.PasswordEncoder;

public class PasswordEncoderAdapter implements PasswordEncoder{
	
	private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
	
	PasswordEncoderAdapter(){this.passwordEncoder = new BCryptPasswordEncoder();}

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	@Override
	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}


}
