package com.studentLotto.passwordreset;

import org.hibernate.validator.constraints.*;

public class PasswordResetForm { 
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";
	
    @NotBlank(message = PasswordResetForm.NOT_BLANK_MESSAGE)
	@Email(message = PasswordResetForm.EMAIL_MESSAGE)
	private String email;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}