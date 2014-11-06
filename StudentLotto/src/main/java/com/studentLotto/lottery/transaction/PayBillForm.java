package com.studentLotto.lottery.transaction;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.studentLotto.account.Account;

public class PayBillForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	private String firstName;
	private String lastName;
	
	/**
	 * Comma delimited list of donationIDs
	 */
	private String donationIDs;
	
	public PayBillForm(){
		
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	private double amount;
	private String number;
	private String securityCode;
	
	@NotBlank(message = PayBillForm.NOT_BLANK_MESSAGE)
	private String billingStreetAddress;

	@NotBlank(message = PayBillForm.NOT_BLANK_MESSAGE)
	private String billingCity;

	@NotBlank(message = PayBillForm.NOT_BLANK_MESSAGE)
	private String billingState = "PA";

	@NotBlank(message = PayBillForm.NOT_BLANK_MESSAGE)
	private String billingZip;
	

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getBillingZip() {
		return billingZip;
	}

	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDonationIDs() {
		return donationIDs;
	}

	public void setDonationIDs(String donationIDs) {
		this.donationIDs = donationIDs;
	}

	public CreditCardTransaction createCCTransaction(Account account) {
		CreditCardTransaction ccTransaction = new CreditCardTransaction();
		ccTransaction.setAccount(account);
		ccTransaction.setAddressCity(this.getBillingCity());
		ccTransaction.setAddressLine1(this.getBillingStreetAddress());
		ccTransaction.setAddressState(this.getBillingState());
		ccTransaction.setAddressZip(this.getBillingZip());
		ccTransaction.setAmount(this.getAmount());
		ccTransaction.setFirstName(this.getFirstName());
		ccTransaction.setLastName(this.getLastName());
		ccTransaction.setNumber(this.getNumber());
		ccTransaction.setSecurityCode(this.getSecurityCode());
		ccTransaction.setTransactionDate(new Date());
		return ccTransaction;
	}

	public String getBillingStreetAddress() {
		return billingStreetAddress;
	}

	public void setBillingStreetAddress(String billingStreetAddress) {
		this.billingStreetAddress = billingStreetAddress;
	}
	
	


}
