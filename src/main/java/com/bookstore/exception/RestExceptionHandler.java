package com.bookstore.exception;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bookstore.exception.model.ErrorResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<ErrorResponse> handle(AuthenticationFailedException exception) {
		logger.error("Authentication failed exception occurred.", exception);
		ErrorResponse errorResponse = new ErrorResponse("AuthenticationFailedException", System.currentTimeMillis(),
				Collections.singletonList(exception.getMessage()));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handle(NotFoundException exception) {
		logger.error("Get data failed exception occurred.", exception);
		ErrorResponse errorResponse = new ErrorResponse("NotFoundException", System.currentTimeMillis(),
				Collections.singletonList(exception.getMessage()));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PasswordEncryptionException.class)
	public ResponseEntity<ErrorResponse> handle(PasswordEncryptionException exception) {
		logger.error("PasswordEncryption failed exception occurred.", exception);
		ErrorResponse errorResponse = new ErrorResponse("PasswordEncryptionException", System.currentTimeMillis(),
				Collections.singletonList(exception.getMessage()));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handle(ConstraintViolationException exception) {
		List<String> errorMessages = exception.getConstraintViolations().stream()
				.map(violation -> this.getMessage(violation.getMessageTemplate(), violation.getInvalidValue()))
				.collect(Collectors.toList());
		ErrorResponse errorResponse = new ErrorResponse("ConstraintViolationException", System.currentTimeMillis(),
				errorMessages);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handle(AccessDeniedException exception) {
		ErrorResponse errorResponse = new ErrorResponse("AccessDeniedException", System.currentTimeMillis(),
				Collections.singletonList(exception.getMessage()));
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException() {
		ErrorResponse errorResponse = new ErrorResponse("GenericException", System.currentTimeMillis(),
				Collections.singletonList("Generic exception occurred."));
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private String getMessage(FieldError error) {
		return getMessage(error.getDefaultMessage(), error.getArguments());
	}

	private String getMessage(String messageKey, Object... args) {
		return messageSource.getMessage(messageKey, args, messageKey, LocaleContextHolder.getLocale());
	}

}
