package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public Optional<Customer> findByEmailAndPassword(String email, String password);

}
