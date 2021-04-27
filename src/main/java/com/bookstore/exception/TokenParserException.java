package com.bookstore.exception;

public class TokenParserException extends RuntimeException {
	private static final long serialVersionUID = 1873360659591145850L;

	public TokenParserException(Exception e) {
		super("JWT Token could not parsed, ", e);
	}
}
