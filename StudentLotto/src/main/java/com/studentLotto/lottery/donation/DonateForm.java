package com.studentLotto.lottery.donation;

public class DonateForm {
	
	public DonateForm(){
		amount = 0.0;
		universityId = -1;
	}
	
	private long universityId;
	
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

}
