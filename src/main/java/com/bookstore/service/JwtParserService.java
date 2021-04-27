package com.bookstore.service;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bookstore.exception.TokenParserException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtParserService {

	@Value("${secret.key}")
	private String secretKey;

	public Claims decodeJWT(String jwt) {
		try {
			return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(jwt)
					.getBody();
		} catch (Exception ex) {
			throw new TokenParserException(ex);
		}
	}
}