package com.studentLotto.lottery.donation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
				new Lottery(new Date(), null, 0, 0, 0, 0, new Date(), new Date(), 100.0, null));
		assertEquals(form.getAmount(), donation.getAmount(), 0.1);
		assertEquals("test@gmail.com", donation.getAccount().getEmail());
		assertEquals(null, donation.getLottery().getUniversity());
	}
	
	@Test
	public void getTotal(){
		List<Donation> donations = new ArrayList();
		donations.add(new Donation(100.0));
		donations.add(new Donation(50.0));
		donations.add(new Donation(25.0));
		assertEquals(175.0, Donation.getTotal(donations), 0.1);
	}
	
	@Test
	public void getIDs(){
		List<Donation> donations = new ArrayList();
		Donation donation1 = new Donation();
		donation1.setId(1l);
		Donation donation2 = new Donation();
		donation2.setId(2l);
		Donation donation3 = new Donation();
		donation3.setId(3l);
		
		donations.add(donation1);
		donations.add(donation2);
		donations.add(donation3);
		assertEquals("1,2,3", Donation.getDonationIDs(donations));
	}
}
