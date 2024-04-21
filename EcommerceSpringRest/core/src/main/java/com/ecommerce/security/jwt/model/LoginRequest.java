package com.ecommerce.security.jwt.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

	@NotBlank
	@Schema(name = "username", example="usuarioprimero")
	private String username;
	@NotBlank
	@Schema(name = "password", example="UsuarioPrimero1")
	private String password;
}
