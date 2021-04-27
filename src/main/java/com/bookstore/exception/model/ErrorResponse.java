package com.bookstore.exception.model;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
	private String exception;
	private Long timestamp;
	private List<String> errors = new ArrayList<>();

	public ErrorResponse(String exception, Long timestamp, List<String> errors) {
		super();
		this.exception = exception;
		this.timestamp = timestamp;
		this.errors = errors;
	}

	public ErrorResponse() {
		super();
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
