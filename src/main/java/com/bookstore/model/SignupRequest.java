package com.bookstore.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignupRequest implements Serializable {
	private static final long serialVersionUID = -8033547376185189410L;

	public SignupRequest() {
		super();
	}

	public SignupRequest(@NotBlank String firstName, @NotBlank String lastName,
			@Email(message = "request.invalid.email") String email,
			@NotBlank(message = "request.invalid.password") String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Email(message = "request.invalid.email")
	private String email;
	@NotBlank(message = "request.invalid.password")
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
