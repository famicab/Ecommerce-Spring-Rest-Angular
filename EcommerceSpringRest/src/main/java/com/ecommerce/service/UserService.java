package com.ecommerce.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.error.exceptions.NewUserWithDifferentPasswordsException;
import com.ecommerce.model.user.User;
import com.ecommerce.model.user.UserRole;
import com.ecommerce.model.user.dto.CreateUserDTO;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, Long, UserRepository> {

	private final PasswordEncoder passwordEncoder;
	
	public Optional<User> findByUsername(String username){
		return this.repository.findByUsername(username);
	}
	
	public User newUser(CreateUserDTO newUser) {

		if(newUser.getPassword().contentEquals(newUser.getPassword2())) {
			User user = User.builder()
					.username(newUser.getUsername())
					.password(passwordEncoder.encode(newUser.getPassword()))
					.avatar(newUser.getAvatar())
					.roles(Set.of(UserRole.USER))
					.build();
			try {
				return save(user);
			} catch(DataIntegrityViolationException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
			}
			
		} else {
			throw new NewUserWithDifferentPasswordsException();
		}
	}
}
