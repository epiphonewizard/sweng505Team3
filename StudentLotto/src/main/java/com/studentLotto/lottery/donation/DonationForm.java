package com.studentLotto.lottery.donation;

import java.util.Date;

import javax.validation.constraints.Min;

import com.studentLotto.account.Account;
import com.studentLotto.lottery.Lottery;

public class DonationForm {
	
	
	public DonationForm(){
		amount = 0.0;
		universityId = -1;
	}
	
	@Min(value = 1, message="Please Select a University")
	private long universityId;
	
	@Min(value = 1, message="Donations must be at least one dollar")
	private double amount;

	public long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(long universityId) {
		this.universityId = universityId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Donation createDonation(Account account, Lottery lottery) {
		Donation donation = new Donation();
		donation.setAccount(account);
		donation.setAmount(getAmount());
		donation.setLottery(lottery);
		donation.setDonationDate(new Date());
		return donation;
	}

}
