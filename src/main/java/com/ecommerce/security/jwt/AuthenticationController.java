package com.ecommerce.security.jwt;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.user.User;
import com.ecommerce.model.user.UserRole;
import com.ecommerce.model.user.dto.GetUserDTO;
import com.ecommerce.model.user.dto.UserDTOConverter;
import com.ecommerce.security.jwt.model.JwtUserResponse;
import com.ecommerce.security.jwt.model.LoginRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "1. Auth")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;
	private final UserDTOConverter converter;
	
	@Operation(summary = "Login", description = "Generates a JWT Token")
	@PostMapping("/auth/login")
	public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = 
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								loginRequest.getUsername(),
								loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		String jwtToken = tokenProvider.generateToken(authentication);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
				convertUserAndTokenToJwtResponse(user, jwtToken));
	}
	
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "Retrieve info.", description = "If the user is authenticated, will return his personal information")
	@SecurityRequirement(name = "App Bearer Token")
	@GetMapping("/user/me")
	public GetUserDTO me(@AuthenticationPrincipal User user) {
		return converter.convertUserToGetUserDTO(user);
	}

	private JwtUserResponse convertUserAndTokenToJwtResponse(User user, String jwtToken) {
		return JwtUserResponse.jwtUserResponseBuilder()
				.fullname(user.getFullName())
				.email(user.getEmail())
				.username(user.getUsername())
				.avatar(user.getAvatar())
				.roles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toSet()))
				.token(jwtToken)
				.build();
	}
}
