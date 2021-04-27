package com.bookstore.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.AuthenticationRequest;
import com.bookstore.model.AuthenticationResponse;
import com.bookstore.service.AuthenticationService;

@RestController
@RequestMapping(value = "/authenticate")
public class AuthenticationController {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping
	public AuthenticationResponse getToken(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
		logger.info("Successfully logged in");
		return authenticationService.getToken(authenticationRequest);
	}

}
