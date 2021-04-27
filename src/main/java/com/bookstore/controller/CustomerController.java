package com.bookstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.SignupRequest;
import com.bookstore.service.CustomerService;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@PostMapping(value = "/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
		customerService.saveCustomer(signupRequest);
		return new ResponseEntity<>("CREATED", HttpStatus.OK);
	}
}