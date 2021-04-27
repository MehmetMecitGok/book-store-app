package com.bookstore.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFailedException extends AuthenticationException {

	private static final long serialVersionUID = 5630263545976682432L;

	public AuthenticationFailedException(String errorMessage) {
		super(errorMessage);
	}
}
