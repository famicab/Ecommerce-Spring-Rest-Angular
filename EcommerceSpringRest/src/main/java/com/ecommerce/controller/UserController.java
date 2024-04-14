package com.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.user.User;
import com.ecommerce.model.user.dto.CreateUserDTO;
import com.ecommerce.model.user.dto.GetUserDTO;
import com.ecommerce.model.user.dto.UserDTOConverter;
import com.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserDTOConverter userDTOConverter;
	
	@PostMapping("/")
	public ResponseEntity<GetUserDTO> newUser(@RequestBody CreateUserDTO newUser){
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(userDTOConverter.convertUserToGetUserDTO(userService.newUser(newUser)));
	}
	
	@GetMapping("/me")
	public GetUserDTO me(@AuthenticationPrincipal User user) {
		return userDTOConverter.convertUserToGetUserDTO(user);
	}
}
