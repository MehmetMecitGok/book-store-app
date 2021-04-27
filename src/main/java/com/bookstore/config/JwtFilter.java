package com.bookstore.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.bookstore.exception.AuthenticationFailedException;
import com.bookstore.service.AuthenticationService;

@Component
public class JwtFilter extends GenericFilterBean {
	private AuthenticationService authenticationService;

	@Autowired
	public JwtFilter(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
			Authentication userAuthentication = authenticationService.getUserAuthentication(httpRequest);
			SecurityContextHolder.getContext().setAuthentication(userAuthentication);
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (AuthenticationFailedException e) {
			HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
			httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication header not valid.");
		}
	}

}
