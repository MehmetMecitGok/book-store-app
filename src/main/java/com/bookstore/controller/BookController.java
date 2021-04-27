package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.BookRequest;
import com.bookstore.model.BookResponse;
import com.bookstore.service.BookService;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	@Autowired
	private BookService bookService;

	@PostMapping(value = "/save")
	public ResponseEntity<String> saveBook(@RequestBody BookRequest bookRequest) {
		bookService.saveBook(bookRequest);
		return new ResponseEntity<>("CREATED", HttpStatus.OK);
	}

	@GetMapping(value = "/details")
	public BookResponse getBookDetails(@RequestParam Long id) {
		return bookService.getBookDetails(id);
	}

}
