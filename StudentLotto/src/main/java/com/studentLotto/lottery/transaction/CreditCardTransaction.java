package com.studentLotto.lottery.transaction;

import java.io.Serializable;

import javax.persistence.*;

import com.studentLotto.account.Person;
import com.studentLotto.university.University;

import java.sql.Timestamp;


/**
 * The persistent class for the CreditCardTransaction database table.
 * 
 */
@Entity
@NamedQuery(name="CreditCardTransaction.findAll", query="SELECT c FROM CreditCardTransaction c")
public class CreditCardTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private double amount;

	private String number;


	private String securityCode;

	private Timestamp transactionDate;

	public CreditCardTransaction() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

	public Timestamp getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	@ManyToOne
	@JoinColumn(name="personID", referencedColumnName="id")
	private Person person;
	
	public Person getPerson(){
		return this.person;
	}
	
	public void setPerson(Person person){
		this.person = person;
	}

}