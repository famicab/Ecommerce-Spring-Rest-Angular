package com.ecommerce.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.error.exceptions.NotEnoughPrivilegesException;
import com.ecommerce.model.user.User;
import com.ecommerce.model.user.dto.CreateUserDTO;
import com.ecommerce.model.user.dto.GetUserDTO;
import com.ecommerce.model.user.dto.UpdateUserDTO;
import com.ecommerce.model.user.dto.UserDTOConverter;
import com.ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@Tag(name = "2. User", description = "User Endpoint")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserDTOConverter userDTOConverter;
	
	@Operation(summary = "Create new user.")
	@PostMapping(path="/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserDTO> newUser(@RequestBody CreateUserDTO newUser){
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(userDTOConverter.convertUserToGetUserDTO(userService.newUser(newUser)));
	}
	
	//TODO What should the Response body return 
	@Operation(summary = "Change password",description = "Performs an update on the password. Old password must be the same, and new passwords must match")
	@SecurityRequirement(name = "App Bearer Token")
	@PatchMapping(path="/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UpdateUserDTO> updatePassword(@RequestBody UpdateUserDTO userDTO){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = (User) authentication.getPrincipal();
        Optional<User> user = userService.findByUsername(userDTO.getUsername());
        if(user.isPresent()) {
        	return ResponseEntity.status(HttpStatus.CREATED)
    				.body(userDTOConverter.converUserToUpdateUserDTO(userService.updatePassword(userDTO)));
        } else {
        	throw new NotEnoughPrivilegesException();
        }
        
		
	}
	
}
