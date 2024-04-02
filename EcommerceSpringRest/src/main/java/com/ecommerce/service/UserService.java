package com.ecommerce.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.model.user.User;
import com.ecommerce.model.user.UserRole;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, Long, UserRepository> {

	private final PasswordEncoder passwordEncoder;
	
	public Optional<User> findByUsername(String username){
		return this.repository.findByUsername(username);
	}
	
	public User newUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Set.of(UserRole.USER));
		return save(user);
	}
}
