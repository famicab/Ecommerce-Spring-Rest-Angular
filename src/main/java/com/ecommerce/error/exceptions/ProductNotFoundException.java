package com.ecommerce.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4568882323835593074L;


	public ProductNotFoundException(Long id) {
		super("Couldn't find product with id: " + id);
	}


	public ProductNotFoundException() {
		super("Product not found.");
	}
}
