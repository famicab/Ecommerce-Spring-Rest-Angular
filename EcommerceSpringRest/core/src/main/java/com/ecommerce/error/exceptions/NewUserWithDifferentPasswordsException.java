package com.ecommerce.error.exceptions;

public class NewUserWithDifferentPasswordsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2851591397471358693L;

	public NewUserWithDifferentPasswordsException() {
		super("Passwords don't match");
	}
}
