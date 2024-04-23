package com.ecommerce.upload;

public class StorageFileNotFoundException extends StorageException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9051710376465798910L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
