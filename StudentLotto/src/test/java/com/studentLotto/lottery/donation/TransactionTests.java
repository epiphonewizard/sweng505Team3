package com.studentLotto.lottery.donation;

import org.junit.Test;

import com.studentLotto.account.Account;
import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.transaction.CreditCardTransaction;
import com.studentLotto.lottery.transaction.PayBillForm;

import static org.junit.Assert.*;

public class TransactionTests {
	@Test
	public void creditCardTransactionConstructor(){
		CreditCardTransaction cc = new CreditCardTransaction();
		cc.setAddressCity("Portland");
		assertEquals("Portland", cc.getAddressCity());
	}

	@Test
	public void payBillFormConstructor(){
		PayBillForm form = new PayBillForm();
		form.setFirstName("Bob");
		form.setLastName("Jones");
		assertEquals("Bob", form.getFirstName());
		assertEquals("Jones", form.getLastName());
	}
	
	@Test
	public void createCCTransaction(){
		PayBillForm form = new PayBillForm();
		form.setFirstName("Bob");
		form.setLastName("Jones");
		form.setBillingCity("Portland");
		form.setBillingState("OR");
		form.setBillingStreetAddress("NW Naito");
		form.setBillingZip("97210");
		form.setAmount(100);
		form.setNumber("1234");
		form.setSecurityCode("999");
		CreditCardTransaction cc = form.createCCTransaction(null);
		assertEquals("Bob", cc.getFirstName());
		assertEquals("Jones", cc.getLastName());
		assertEquals("Portland", cc.getAddressCity());
		assertEquals("OR", cc.getAddressState());
		assertEquals("NW Naito", cc.getAddressLine1());
		assertEquals("97210", cc.getAddressZip());
		assertEquals(100.0, cc.getAmount(), 0.1);
		assertEquals("1234", cc.getNumber());
		assertEquals("999", cc.getSecurityCode());
		assertEquals(null, cc.getAccount());
	}
}
