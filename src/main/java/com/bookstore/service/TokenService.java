package com.bookstore.service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bookstore.entity.Customer;
import com.bookstore.exception.AuthenticationFailedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenService {
	@Value("${secret.key}")
	private String secretKey;

	@Autowired
	private JwtParserService jwtParserService;

	public String createToken(Customer customer) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("customerId", customer.getCustomerId());
		claims.put("firstName", customer.getFirstName());
		claims.put("lastName", customer.getLastName());
		claims.put("email", customer.getEmail());

		return generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		// convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		// manipulate date
		c.add(Calendar.YEAR, 1); // expiration date is 1 year later
		Date expirationDate = c.getTime();

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expirationDate)
				.signWith(signatureAlgorithm, signingKey).compact();
	}

	public Claims getTokenClaims(HttpServletRequest httpServletRequest) {
		Claims claims = jwtParserService.decodeJWT(getTokenFromHeader(httpServletRequest));
		validateToken(claims);
		return claims;
	}

	private String getTokenFromHeader(HttpServletRequest httpServletRequest) {
		String authenticationHeader = httpServletRequest.getHeader("Authorization");
		boolean startWithBearer = StringUtils.startsWith(authenticationHeader, "Bearer");
		String[] headerParams = StringUtils.split(authenticationHeader, StringUtils.SPACE);
		boolean headerParamSizeIsTwo = ArrayUtils.getLength(headerParams) == 2;
		boolean isAuthenticationHeaderValid = authenticationHeader != null && startWithBearer && headerParamSizeIsTwo;
		if (!isAuthenticationHeaderValid) {
			throw new AuthenticationFailedException("Authentication header not valid");
		}
		return authenticationHeader.split(StringUtils.SPACE)[1];
	}

	private void validateToken(Claims tokenData) {
		if (DateTime.now().isAfter(tokenData.getExpiration().getTime())) {
			throw new AuthenticationFailedException("Token expired");
		}
	}

}
