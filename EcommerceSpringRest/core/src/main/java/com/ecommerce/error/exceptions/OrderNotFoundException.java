package com.ecommerce.error.exceptions;

public class OrderNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3832572299333509919L;

	public OrderNotFoundException() {
		super("No orders found");
	}
	
	public OrderNotFoundException(Long id) {
		super("No order found with id "+ id);
	}
}
