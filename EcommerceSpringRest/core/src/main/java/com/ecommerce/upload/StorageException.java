package com.ecommerce.upload;

public class StorageException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6496207121238298987L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
