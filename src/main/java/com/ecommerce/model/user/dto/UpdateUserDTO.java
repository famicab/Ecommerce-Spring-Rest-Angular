package com.ecommerce.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateUserDTO {

	private String username;
	private String oldPassword;
	private String newPassword;
	private String newPassword2;
}
