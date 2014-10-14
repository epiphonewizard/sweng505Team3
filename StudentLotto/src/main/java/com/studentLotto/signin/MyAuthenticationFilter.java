package com.studentLotto.signin;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.utilities.AccountActivation;
import com.studentLotto.utilities.AccountActivationRepository;

public class MyAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	@Autowired
	private AccountActivationRepository repo;

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain,
			Authentication authResult) throws IOException, ServletException {

		// Get the login username
		String email = request.getParameter("j_username");
		// Fetch the account activation info from the database
		AccountActivation ac = repo.findByEmail(email);
		// if account is active then relay the request to the next filter in the
		// chain
		if (ac.getActivationStatus() == 1) {
			super.successfulAuthentication(request, response, filterChain,
					authResult);
		}
		// if account inactive then direct to next chain filter to not login the
		// user
		else {
			authResult.setAuthenticated(false);
			super.unsuccessfulAuthentication(request, response,
					new PreAuthenticatedCredentialsNotFoundException(
							"INACTIVE ACCOUNT"));
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}

}