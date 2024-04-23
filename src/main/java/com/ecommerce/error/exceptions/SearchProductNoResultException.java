package com.ecommerce.error.exceptions;

public class SearchProductNoResultException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3191296177443504705L;

	public SearchProductNoResultException() {
		super("Your search did not match any product.");
	}
	
	public SearchProductNoResultException(String txt) {
		super(String.format("Your search %s did not match any product.", txt));
	}
}
