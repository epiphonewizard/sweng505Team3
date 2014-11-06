package com.studentLotto.lottery.transaction;

import java.io.Serializable;

import javax.persistence.*;

import com.studentLotto.account.Account;
import com.studentLotto.account.Person;
import com.studentLotto.university.University;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the CreditCardTransaction database table.
 * 
 */
@Entity
@NamedQuery(name="CreditCardTransaction.findAll", query="SELECT c FROM CreditCardTransaction c")
public class CreditCardTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue	
	private Long id;

	private double amount;

	private String number;


	private String securityCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;
	
	private String addressCity;

	private String addressLine1;

	private String addressLine2;

	private String addressState;

	private String addressZip;
	
	private String firstName;
	private String lastName;

	public CreditCardTransaction() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


	public String getSecurityCode() {
		return this.securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="id")
	private Account account;
	
	public Account getAccount(){
		return this.account;
	}
	
	public void setAccount(Account account){
		this.account = account;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
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

}