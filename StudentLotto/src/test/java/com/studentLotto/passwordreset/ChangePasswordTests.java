package com.studentLotto.passwordreset;

import static org.junit.Assert.*;

import org.junit.Test;
public class ChangePasswordTests {
	
	@Test
	public void getterSetterTests(){
		String password = "password";
		String confirm = "confirm";
		ChangePasswordForm form = new ChangePasswordForm();
		form.setPassword(password);
		form.setConfirm(confirm);
		assertEquals(password, form.getPassword());
		assertEquals(confirm, form.getConfirm());
	}

}
