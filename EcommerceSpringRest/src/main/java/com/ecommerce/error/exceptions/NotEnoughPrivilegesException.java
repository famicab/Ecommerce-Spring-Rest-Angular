package com.ecommerce.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotEnoughPrivilegesException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6350352292011598386L;
	
	public NotEnoughPrivilegesException(Long id) {
		super("Access Denied: User " + id +" do not have the necessary privileges to perform this operation.\n"
				+ "Please contact your system administrator for assistance or request the appropriate permissions to access this resource.");
	}


	public NotEnoughPrivilegesException() {
		super("Access Denied: You do not have the necessary privileges to perform this operation.\n"
				+ "Please contact your system administrator for assistance or request the appropriate permissions to access this resource.");
	}

}
