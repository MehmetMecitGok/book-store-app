package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.OrderResponse;
import com.bookstore.model.UserAuthentication;
import com.bookstore.service.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping(value = "/new")
	public ResponseEntity<String> newOrder(@AuthenticationPrincipal UserAuthentication userAuthentication,
			@RequestParam Long bookId) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		orderService.createOrder(((Long) authentication.getDetails()).longValue(), bookId);
		return new ResponseEntity<>("CREATED", HttpStatus.OK);
	}

	@GetMapping(value = "/details")
	public OrderResponse getOrderDetails(@AuthenticationPrincipal UserAuthentication userAuthentication,
			@RequestParam Long orderId) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return orderService.getOrderDetails(((Long) authentication.getDetails()).longValue(), orderId);
	}

	@GetMapping(value = "/all")
	public List<OrderResponse> getOrders(@AuthenticationPrincipal UserAuthentication userAuthentication) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return orderService.getOrders(((Long) authentication.getDetails()).longValue());
	}
}
