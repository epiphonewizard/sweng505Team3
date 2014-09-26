package com.studentLotto.account;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountValidationTest {

	Account account;

	public AccountValidationTest() {
		account = new Account();
		// reset test database here!
	}

	@Test
	public void isActivationKeyUniqueTest() {
		// start with a new account object
		account = new Account();
		// Check if an activation key is unique. True is expected as we havn't
		// added anything to the database yet.
		assertEquals(account.isActivationKeyUnique("abcdefg"), true);
		// save account activation key
		account.saveAccountActivationInfo("abcdefg", 12345);
		// confirm that the key is not unique
		assertEquals(account.isActivationKeyUnique("abcdefg"), false);
	}

	@Test
	public void generateAccountActivationkeyTest() {
		String email = null;
		String password = null;
		assertEquals(account.generateAccountActivationKey(email, password), "");

		// If email is null, we should not generate an activation key
		email = null;
		password = "password";
		assertEquals(account.generateAccountActivationKey(email, password), "");

		// If password is null, we should not generate an activation key
		email = "email@email.email";
		password = null;
		assertEquals(account.generateAccountActivationKey(email, password), "");

		// if email is in the wrong format, we should not create an activation
		// key (a@a.a)
		email = "wrongEmailFormat";
		password = "password";
		assertEquals(account.generateAccountActivationKey(email, password), "");

		// the input seem correct, the activation key should be created
		email = "email@email.email";
		password = "password";
		assertTrue(account.generateAccountActivationKey(email, password)
				.length() > 30);
	}

	@Test
	public void saveAccountActivationInfoTest() {
		String activationKey;
		long accountId;

		// save brand new account activation info
		activationKey = "abcdefg";
		accountId = 12345678;
		assertTrue(account.saveAccountActivationInfo(activationKey, accountId));

		// create account activation info for the same accoutn ID different
		// activation key
		// save brand new account activation info
		activationKey = "abcdeff";
		accountId = 12345678;
		// should not work because the account ID was already assigned an
		// activation key
		assertFalse(account.saveAccountActivationInfo(activationKey, accountId));

		// create a duplicate account activation info for a new accoutn ID
		// save brand new account activation info
		activationKey = "abcdefg";
		accountId = 12345679;
		// should not work because the activation key should be unique on a per
		// account basis
		assertFalse(account.saveAccountActivationInfo(activationKey, accountId));

		// save brand new account activation info
		activationKey = "abcdeff";
		accountId = 12345679;
		assertTrue(account.saveAccountActivationInfo(activationKey, accountId));
	}

	@Test
	public void sendEmailTest() {
		String recipient;
		String title;
		String body;

		// We cannot send a message to null
		recipient = null;
		title = null;
		body = null;
		assertFalse(account.sendEmail(recipient, title, body));

		// We cannot send a message to null
		recipient = null;
		title = "Test title";
		body = null;
		assertFalse(account.sendEmail(recipient, title, body));

		// We cannot send a message to null
		recipient = null;
		title = "Test title";
		body = "body";
		assertFalse(account.sendEmail(recipient, title, body));

		// We cannot send a message to badly formatted email
		recipient = "wrongEmailFormat";
		title = "testTitle";
		body = "body";
		assertFalse(account.sendEmail(recipient, title, body));

		// We cannot send a message to null
		recipient = "e@e.e";
		title = "Title";
		body = "body";
		assertTrue(account.sendEmail(recipient, title, body));

	}

	@Test
	public void emailAccountActivationTest() {
		String email = null;
		String password = null;

		assertEquals(account.generateAccountActivationKey(email, password), "");

		// If email is null, we should not email
		email = null;
		password = "password";
		assertFalse(account.emailAccountActivation(email, password));

		// If password is null, we should not email
		email = "email@email.email";
		password = null;
		assertFalse(account.emailAccountActivation(email, password));

		// if email is in the wrong format, we should not email
		// key (a@a.a)
		email = "wrongEmailFormat";
		password = "password";
		assertFalse(account.emailAccountActivation(email, password));

		// the input seem correct
		email = "email@email.email";
		password = "password";
		assertTrue(account.emailAccountActivation(email, password));
	}

}
