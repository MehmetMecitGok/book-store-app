package com.bookstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.entity.Customer;
import com.bookstore.model.SignupRequest;
import com.bookstore.repository.CustomerRepository;
import com.bookstore.util.Util;

@Service
public class CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private Util util;

	public void saveCustomer(SignupRequest signupRequest) {
		Customer customer = convert(signupRequest);
		customerRepository.save(customer);

		logger.info("Customer successfully saved: " + customer.toString());
	}

	private Customer convert(SignupRequest registerRequest) {
		Customer customer = new Customer();
		customer.setFirstName(registerRequest.getFirstName());
		customer.setLastName(registerRequest.getLastName());
		customer.setEmail(registerRequest.getEmail());
		customer.setPassword(util.encryptPassword(registerRequest.getPassword()));
		return customer;
	}
}
