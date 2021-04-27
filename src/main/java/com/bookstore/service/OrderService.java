package com.bookstore.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Customer;
import com.bookstore.entity.Order;
import com.bookstore.exception.NotFoundException;
import com.bookstore.model.OrderResponse;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CustomerRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.util.OrderStatus;

@Service
public class OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private CustomerRepository customerRepository;

	public void createOrder(Long customerId, Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);

		if (book.isEmpty()) {
			throw new NotFoundException(bookId + " is not found.");
		}

		if (book.get().getStock() <= 0) {
			throw new NotFoundException("Not enough stock found");
		}

		Order order = convert(customerId, bookId);
		orderRepository.save(order);

		book.get().setStock(book.get().getStock() - 1);
		bookRepository.save(book.get());

		logger.info("New order with {} book id and  and {} customer id created", bookId, customerId);
	}

	private Order convert(Long customerId, Long bookId) {
		Order order = new Order();
		order.setCustomerId(customerId);
		order.setOrderDate(new Date());
		order.setBookId(bookId);
		order.setStatus(OrderStatus.NEW_ORDER);
		return order;
	}

	public OrderResponse getOrderDetails(Long customerId, Long orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);

		if (!optionalOrder.get().getCustomerId().equals(customerId)) {
			throw new AccessDeniedException("You have not permission to view this order");
		}

		if (optionalOrder.isEmpty()) {
			throw new NotFoundException("Order not found by id : " + orderId);
		}

		return convert(optionalOrder.get());
	}

	private OrderResponse convert(Order order) {
		OrderResponse response = new OrderResponse();
		response.setOrderId(order.getOrderId());
		response.setBookId(order.getBookId());
		response.setCustomerId(order.getCustomerId());
		response.setOrderDate(order.getOrderDate());
		response.setStatus(order.getStatus());
		return response;
	}

	public List<OrderResponse> getOrders(Long customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isEmpty()) {
			throw new NotFoundException(customerId + " is not found.");
		}

		List<Order> orderList = orderRepository.findByCustomerId(customerId);

		List<OrderResponse> list = new ArrayList<>();
		orderList.forEach(f -> {
			list.add(convert(f));
		});

		return list;
	}

}
