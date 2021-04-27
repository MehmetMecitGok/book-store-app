package com.bookstore.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BookRequest {
	@NotBlank(message = "bookRequest.name.notBlank")
	private String name;
	@Size(message = "bookRequest.stock.size")
	private Integer stock;
	@Digits(message = "bookRequest.price.digits", fraction = 2, integer = 10)
	private Double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
