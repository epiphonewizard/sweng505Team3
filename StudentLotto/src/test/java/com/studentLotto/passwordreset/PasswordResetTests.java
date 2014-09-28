package com.studentLotto.passwordreset;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordResetTests {
	
	@Test
	public void passwordResetFormGetters(){
		String email = "MyEmail";
		PasswordResetForm form = new PasswordResetForm();
		form.setEmail(email);
		assertEquals(email, form.getEmail());
	}
	

}
