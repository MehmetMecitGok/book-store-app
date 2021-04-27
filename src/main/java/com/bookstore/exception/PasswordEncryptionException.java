package com.bookstore.exception;

public class PasswordEncryptionException extends RuntimeException {

	private static final long serialVersionUID = 3290095282080659546L;

	public PasswordEncryptionException(Exception ex) {
		super("Password could not be encrypted, ", ex);
	}
}