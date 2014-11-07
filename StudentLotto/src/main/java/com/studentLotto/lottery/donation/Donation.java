package com.studentLotto.lottery.donation;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.studentLotto.account.Account;
import com.studentLotto.account.Person;
import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.transaction.CreditCardTransaction;
import com.studentLotto.university.University;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Donation database table.
 * 
 */
@Entity

@NamedQueries({
	@NamedQuery(name="Donation.findAll", query="SELECT d FROM Donation d"),
	@NamedQuery(name = Donation.GET_UNPAID_FOR_ACCOUNT, query = "select d from Donation d WHERE accountId = :accountId and paymentComplete = 0"),
})
public class Donation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String GET_UNPAID_FOR_ACCOUNT = "Donation.GET_UNPAID_FOR_ACCOUNT";

	@Id
	@GeneratedValue
	private Long id;

	private double amount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date donationDate;

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean paymentComplete;


	public Donation() {
	}
	
	public Donation(double amount) {
		setAmount(amount);
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

	public Date getDonationDate() {
		return this.donationDate;
	}

	public void setDonationDate(Date donationDate) {
		this.donationDate = donationDate;
	}

	public boolean getPaymentComplete() {
		return this.paymentComplete;
	}

	public void setPaymentComplete(boolean paymentComplete) {
		this.paymentComplete = paymentComplete;
	}

	
	
	@ManyToOne
	@JoinColumn(name="accountID", referencedColumnName="id")
	private Account account;
	
	public Account getAccount(){
		return this.account;
	}
	
	public void setAccount(Account account){
		this.account = account;
	}

	
	@ManyToOne
	@JoinColumn(name="lotteryID", referencedColumnName="id")
	private Lottery lottery;
	
	public Lottery getLottery(){
		return this.lottery;
	}
	
	public void setLottery(Lottery lottery){
		this.lottery = lottery;
	}
	
	
	@ManyToOne
	@JoinColumn(name="ccTransactionId", referencedColumnName="id")
	private CreditCardTransaction ccTransaction;
	
	public CreditCardTransaction getCreditCardTransaction(){
		return this.ccTransaction;
	}
	
	public void setCreditCardTransaction(CreditCardTransaction ccTransaction){
		this.ccTransaction = ccTransaction;
	}
	
	public static Double getTotal(List<Donation> donations) {
		Double total = 0.0;
		for(Donation donation : donations){
			total = total + donation.getAmount();
		}
		return total;
	}
	
	public static String getDonationIDs(List<Donation> donations) {
		StringBuilder sb = new StringBuilder();
		for(Donation donation : donations){
			if(sb.length() > 0) 
				sb.append(",");
			sb.append(donation.getId().toString());
		}
		return sb.toString();
	}

}