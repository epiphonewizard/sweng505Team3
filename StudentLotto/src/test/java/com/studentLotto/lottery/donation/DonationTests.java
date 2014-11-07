package com.studentLotto.lottery.donation;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.studentLotto.account.Account;
import com.studentLotto.lottery.Lottery;

import static org.junit.Assert.*;

public class DonationTests {

	@Test
	public void constructorTests(){
		Donation donation = new Donation();
		donation.setAmount(0.0);
		donation.setPaymentComplete(false);
		donation.setAccount(new Account("test@gmail.com", "password"));
		donation.setLottery(new Lottery());
		
		assertEquals(0.0, donation.getAmount(), 0.1);
		assertEquals(false, donation.getPaymentComplete());
		assertEquals("test@gmail.com", donation.getAccount().getEmail());
	}
	
	@Test
	public void constructDonationForm(){
		DonationForm form = new DonationForm();
		assertEquals(0.0, form.getAmount(), 0.1);
		assertEquals(-1, form.getUniversityId());
	}
	
	@Test 
	public void createDonation(){
		DonationForm form = new DonationForm();
		form.setAmount(100.5);
		Donation donation = form.createDonation(
				new Account("test@gmail.com", "password"), 
				new Lottery(new Date(), new Date(), new Date(), BigDecimal.valueOf(100), null));
		assertEquals(form.getAmount(), donation.getAmount(), 0.1);
		assertEquals("test@gmail.com", donation.getAccount().getEmail());
		assertEquals(100, donation.getLottery().getMaxWinnings().doubleValue(), 0.1);
		assertEquals(null, donation.getLottery().getUniversity());
	}
}
