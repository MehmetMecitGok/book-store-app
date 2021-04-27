package com.bookstore.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.entity.Book;
import com.bookstore.exception.NotFoundException;
import com.bookstore.model.BookRequest;
import com.bookstore.model.BookResponse;
import com.bookstore.repository.BookRepository;

@Service
public class BookService {
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepository bookRepository;

	public void saveBook(BookRequest bookRequest) {
		Book book = convert(bookRequest);
		bookRepository.save(book);

		logger.info("New Book Saved. BookId : {} , BookName : {}, Stock Count : {}, Price : {}", book.getBookId(),
				book.getName(), book.getStock(), book.getPrice());
	}

	private Book convert(BookRequest bookRequest) {
		Book book = new Book();
		book.setName(bookRequest.getName());
		book.setStock(bookRequest.getStock());
		book.setPrice(bookRequest.getPrice());
		return book;
	}

	public BookResponse getBookDetails(Long id) {
		Optional<Book> book = bookRepository.findById(id);

		if (book.isEmpty()) {
			throw new NotFoundException("Book not found by id : " + id);
		}

		return new BookResponse(book.get().getBookId(), book.get().getName(), book.get().getStock(),
				book.get().getPrice());
	}
}
