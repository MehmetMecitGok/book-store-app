package com.bookstore.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.bookstore.entity.Customer;
import com.bookstore.exception.AuthenticationFailedException;
import com.bookstore.model.AuthenticationRequest;
import com.bookstore.model.AuthenticationResponse;
import com.bookstore.model.UserAuthentication;
import com.bookstore.repository.CustomerRepository;
import com.bookstore.util.Util;

import io.jsonwebtoken.Claims;

@Service
public class AuthenticationService {
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private Util util;

	public AuthenticationResponse getToken(AuthenticationRequest authenticationRequest) {
		String encryptedPassword = util.encryptPassword(authenticationRequest.getPassword());
		Optional<Customer> authCustomer = customerRepository.findByEmailAndPassword(authenticationRequest.getEmail(),
				encryptedPassword);

		if (authCustomer.isEmpty()) {
			throw new AuthenticationFailedException(authenticationRequest.getEmail());
		}

		return convert(authCustomer.get());
	}

	private AuthenticationResponse convert(Customer customer) {
		String token = tokenService.createToken(customer);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setToken(token);
		authenticationResponse.setFirstName(customer.getFirstName());
		authenticationResponse.setLastName(customer.getLastName());
		authenticationResponse.setCustomerId(customer.getCustomerId());
		authenticationResponse.setEmail(customer.getEmail());
		return authenticationResponse;
	}

	public Authentication getUserAuthentication(HttpServletRequest httpServletRequest) {
		Claims claims = tokenService.getTokenClaims(httpServletRequest);
		return new UserAuthentication(Long.parseLong(claims.get("customerId").toString()),
				claims.get("firstName").toString(), claims.get("lastName").toString(), claims.get("email").toString());
	}

}
