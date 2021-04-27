package com.bookstore.model;

public class BookResponse {

	public BookResponse(Long id, String name, Integer stock, Double price) {
		super();
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.price = price;
	}

	private Long id;
	private String name;
	private Integer stock;
	private Double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
