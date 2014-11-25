package com.studentLotto.student;

import java.io.Serializable;

import javax.persistence.*;

import com.studentLotto.lottery.Lottery;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the LotteryTicket database table.
 * 
 */
@Entity
@Table(name = "LotteryTicket")
@NamedQueries({
	@NamedQuery(name = LotteryTicket.FIND_ALL, query = "SELECT l FROM LotteryTicket l"),
	@NamedQuery(name = LotteryTicket.FIND_STUDENT_RESERVED_TICEKT_FOR_LOTTERY, query = "SELECT l FROM LotteryTicket l WHERE lotteryId = :lotteryId AND l.studentId = :studentId"),
	@NamedQuery(name = LotteryTicket.FIND_STUDENT_UNPAID_TICEKT_FOR_UPCOMING_LOTTERY, query = "select l from LotteryTicket l WHERE lotteryId = :lotteryId AND l.studentId = :studentId  AND paymentComplete = 0"),
	@NamedQuery(name = LotteryTicket.FIND_TICKETS_FOR_LOTTERY, query = "select l from LotteryTicket l WHERE lotteryId = :lotteryId AND paymentComplete = 1")
})

public class LotteryTicket implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FIND_ALL = "LotteryTicket.findAll";
	public static final String FIND_STUDENT_RESERVED_TICEKT_FOR_LOTTERY = "LotteryTicket.findStudentReservedTicketForLottery";
	public static final String FIND_STUDENT_UNPAID_TICEKT_FOR_UPCOMING_LOTTERY = "LotteryTicket.getUnpaidStudentTicket";
	public static final String FIND_TICKETS_FOR_LOTTERY = "LotteryTicket.findTicketsForLottery";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(nullable = false)
	private int fifthNumber;

	@Column(nullable = false)
	private int firstNumber;

	@Column(nullable = false)
	private int fourthNumber;

	@Column(nullable = false)
	private double payout;

	@Column(nullable = false)
	private int secondNumber;

	@Column(nullable = false)
	private int sixthNumber;

	@Column(nullable = false)
	private long studentId;

	@Column(nullable = false)
	private int thirdNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date transactionTimeStamp;

	@Column(length = 500)
	private String winDescription;

	@Column(nullable = false)
	private int winFlag;

	@Column(nullable = false)
	private int paymentComplete;

	@Column(nullable = false)
	private long ccTransactionId;

	// @ManyToOne
	// @JoinColumn(name="ccTransactionId", referencedColumnName="id")

	// private CreditCardTransaction ccTransaction;

	@ManyToOne
	@JoinColumn(name = "lotteryID", referencedColumnName = "id")
	private Lottery lottery;

	// @Column(nullable = false)
	// private int lotteryId;

	@Transient
	private String stringRepresentation;

	/**
	 * @return the stringRepresentation
	 */
	public String getStringRepresentation() {
		stringRepresentation = "[" + firstNumber + ", " + secondNumber + ", "
				+ thirdNumber + ", " + fourthNumber;

		if (lottery.getNumberOfBallsAvailable() == 5) {
			stringRepresentation += ", " + fifthNumber;
		} else if (lottery.getNumberOfBallsAvailable() == 6) {
			stringRepresentation += ", " + sixthNumber;
		}
		stringRepresentation += "]";

		return stringRepresentation;
	}

	/**
	 * @param stringRepresentation
	 *            the stringRepresentation to set
	 */
	public void setStringRepresentation(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	// public CreditCardTransaction getCcTransaction() {
	// return ccTransaction;
	// }

	// public void setCcTransaction(CreditCardTransaction ccTransaction) {
	// this.ccTransaction = ccTransaction;
	// }

	public LotteryTicket() {
	}

	public LotteryTicket(LotteryTicketForm form, long studentId, Lottery lottery) {
		this.firstNumber = form.getFirstNumber();
		this.secondNumber = form.getSecondNumber();
		this.thirdNumber = form.getThirdNumber();
		this.fourthNumber = form.getFourthNumber();
		this.fifthNumber = form.getFifthNumber();
		this.sixthNumber = form.getSixthNumber();
		this.winFlag = 0;
		this.payout = 0.0;
		this.transactionTimeStamp = new Date(System.currentTimeMillis()
				- (24 * 60 * 60 * 1000));
		this.winDescription = "Ticket just purchased. Results pending";
		this.studentId = studentId;
		// this.lotteryId = lotteryId;
		setLottery(lottery);
		this.paymentComplete = 0;
		// this.ccTransactionId = 0;

	}

	public long getCcTransactionId() {
		return ccTransactionId;
	}

	public void setCcTransactionId(long ccTransactionId) {
		this.ccTransactionId = ccTransactionId;
	}

	public int getPaymentComplete() {
		return paymentComplete;
	}

	public void setPaymentComplete(int paymentComplete) {
		this.paymentComplete = paymentComplete;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getFifthNumber() {
		return this.fifthNumber;
	}

	public void setFifthNumber(int fifthNumber) {
		this.fifthNumber = fifthNumber;
	}

	public int getFirstNumber() {
		return this.firstNumber;
	}

	public void setFirstNumber(int firstNumber) {
		this.firstNumber = firstNumber;
	}

	public int getFourthNumber() {
		return this.fourthNumber;
	}

	public void setFourthNumber(int fourthNumber) {
		this.fourthNumber = fourthNumber;
	}

	/*
	 * public int getLotteryId() { return this.lotteryId; }
	 * 
	 * public void setLotteryId(int lotteryId) { this.lotteryId = lotteryId; }
	 */

	public double getPayout() {
		return this.payout;
	}

	public void setPayout(double payout) {
		this.payout = payout;
	}

	public int getSecondNumber() {
		return this.secondNumber;
	}

	public void setSecondNumber(int secondNumber) {
		this.secondNumber = secondNumber;
	}

	public int getSixthNumber() {
		return this.sixthNumber;
	}

	public void setSixthNumber(int sixthNumber) {
		this.sixthNumber = sixthNumber;
	}

	public long getStudentId() {
		return this.studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public int getThirdNumber() {
		return this.thirdNumber;
	}

	public void setThirdNumber(int thirdNumber) {
		this.thirdNumber = thirdNumber;
	}

	public Date getTransactionTimeStamp() {
		return this.transactionTimeStamp;
	}

	public void setTransactionTimeStamp(Date transactionTimeStamp) {
		this.transactionTimeStamp = transactionTimeStamp;
	}

	public String getWinDescription() {
		return this.winDescription;
	}

	public void setWinDescription(String winDescription) {
		this.winDescription = winDescription;
	}

	public int getWinFlag() {
		return this.winFlag;
	}

	public void setWinFlag(int winFlag) {
		this.winFlag = winFlag;
	}

	public static String getTicketIDs(List<LotteryTicket> tickets) {
		StringBuilder sb = new StringBuilder();
		for (LotteryTicket ticket : tickets) {
			if (sb.length() > 0)
				sb.append(",");
			sb.append(String.valueOf(ticket.getId()));
		}
		return sb.toString();
	}

	public double getAmount() {
		return lottery.getLotteryTicketCost();
	}

	public static Double getTotal(List<LotteryTicket> tickets) {
		Double total = 0.0;
		for (LotteryTicket ticket : tickets) {
			total = total + ticket.getAmount();
		}
		return total;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LotteryTicket [firstNumber=" + firstNumber + ", secondNumber="
				+ secondNumber + ", thirdNumber=" + thirdNumber
				+ ", fourthNumber=" + fourthNumber + ",   fifthNumber="
				+ fifthNumber + ", sixthNumber=" + sixthNumber + "]";
	}
	
	public Set<Integer> getTicketNumbers(){
		Set<Integer> ticketNumberSet = new HashSet<>();
		ticketNumberSet.add(this.firstNumber);
		ticketNumberSet.add(this.secondNumber);
		ticketNumberSet.add(this.thirdNumber);
		ticketNumberSet.add(this.fourthNumber);
		ticketNumberSet.add(this.fifthNumber);
		ticketNumberSet.add(this.sixthNumber);
		return ticketNumberSet;
	}

}