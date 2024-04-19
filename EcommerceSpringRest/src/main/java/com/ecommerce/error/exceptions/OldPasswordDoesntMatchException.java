package com.ecommerce.error.exceptions;

public class OldPasswordDoesntMatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1232967641550536604L;
	
	public OldPasswordDoesntMatchException() {
		super("Old password is incorrect");
	}

}
