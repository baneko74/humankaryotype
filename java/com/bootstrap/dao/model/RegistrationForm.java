package com.bootstrap.dao.model;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegistrationForm {

	private String username;
	private String password;
	private String name;
	private String confirm;

	public User toUser(PasswordEncoder encoder) {
		return new User(username, encoder.encode(password), name);
	}
}
