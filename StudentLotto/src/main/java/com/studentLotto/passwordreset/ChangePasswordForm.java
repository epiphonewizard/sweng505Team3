package com.studentLotto.passwordreset;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

@ScriptAssert(lang="javascript", script="_this.password.equals(_this.confirm)", message="Password and Confirm fields must match")
public class ChangePasswordForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
    @NotBlank(message = ChangePasswordForm.NOT_BLANK_MESSAGE)
	private String password;
    
    @NotBlank(message = ChangePasswordForm.NOT_BLANK_MESSAGE)
    private String confirm;

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirm
	 */
	public String getConfirm() {
		return confirm;
	}

	/**
	 * @param confirm the confirm to set
	 */
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
}
