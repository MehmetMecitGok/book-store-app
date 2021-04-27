package com.bookstore.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.bookstore.exception.PasswordEncryptionException;

@Component
public class Util {
	public String encryptPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return Base64.getEncoder().encodeToString(md.digest(password.getBytes(StandardCharsets.UTF_16LE)));
		} catch (Exception ex) {
			throw new PasswordEncryptionException(ex);
		}
	}
}
