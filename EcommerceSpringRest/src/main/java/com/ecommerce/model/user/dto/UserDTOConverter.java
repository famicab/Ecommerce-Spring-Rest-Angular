package com.ecommerce.model.user.dto;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ecommerce.model.user.User;
import com.ecommerce.model.user.UserRole;

@Component
public class UserDTOConverter {

	public GetUserDTO convertUserToGetUserDTO(User user) {
		return GetUserDTO.builder()
				.username(user.getUsername())
				.avatar(user.getAvatar())
				.roles(user.getRoles().stream()
						.map(UserRole::name)
						.collect(Collectors.toSet())
				)
				.build();
	}
}
