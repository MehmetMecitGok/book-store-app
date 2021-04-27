package com.bookstore.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5360457872576489550L;

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
