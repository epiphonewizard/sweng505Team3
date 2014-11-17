package com.studentLotto.lottery;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.studentLotto.university.University;

import java.util.Date;


/**
 * The persistent class for the Lottery database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Lottery.FIND_ALL, query="SELECT l FROM Lottery l"),
	@NamedQuery(name="Lottery.getUpcoming", query="SELECT l FROM Lottery l WHERE now() < l.drawingDate"),
	@NamedQuery(name=Lottery.GET_UPCOMING_FOR_UNIVERSITY, query="SELECT l FROM Lottery l WHERE :now < l.drawingDate and universityId = :universityId ORDER BY l.drawingDate")
	})
public class Lottery implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String GET_UPCOMING_FOR_UNIVERSITY = "Lottery.getUpcomingForUniversity";
	public static final String FIND_ALL = "Lottery.findAll";

	@Id
	@GeneratedValue
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date drawingDate;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean fullMatchGuaranteed;

	private double lotteryTicketCost;

	private int maxTicketsAllowedToPurchase;

	private int numberOfBallsAvailable;

	private int numberOfBallsPicked;

	@Temporal(TemporalType.TIMESTAMP)
	private Date purchaseEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date purchaseStartDate;

	private double studentWinningPercentage;
 
	private String winningNumber;
	
	@ManyToOne
	@JoinColumn(name="universityID", referencedColumnName="id")
	private University university;

	public Lottery() {
	}
	
	public Lottery(Date drawingDate, Boolean fullMatchGuaranteed,
			double lotteryTicketCost, int maxTicketsAllowedToPurchase,
			int numberOfBallsAvailable,
			int numberOfBallsPicked, Date purchaseEndDate,
			Date purchaseStartDate, double studentWinningPercentage,
			University university) {
		super();
		this.drawingDate = drawingDate;
		this.fullMatchGuaranteed = fullMatchGuaranteed;
		this.lotteryTicketCost = lotteryTicketCost;
		this.maxTicketsAllowedToPurchase = maxTicketsAllowedToPurchase;
		this.numberOfBallsAvailable = numberOfBallsAvailable;
		this.numberOfBallsPicked = numberOfBallsPicked;
		this.purchaseEndDate = purchaseEndDate;
		this.purchaseStartDate = purchaseStartDate;
		this.studentWinningPercentage = studentWinningPercentage;
		this.university = university;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDrawingDate() {
		return this.drawingDate;
	}

	public void setDrawingDate(Date drawingDate) {
		this.drawingDate = drawingDate;
	}

	public Boolean getFullMatchGuaranteed() {
		return this.fullMatchGuaranteed;
	}

	public void setFullMatchGuaranteed(Boolean fullMatchGuaranteed) {
		this.fullMatchGuaranteed = fullMatchGuaranteed;
	}

	public double getLotteryTicketCost() {
		return this.lotteryTicketCost;
	}

	public void setLotteryTicketCost(double lotteryTicketCost) {
		this.lotteryTicketCost = lotteryTicketCost;
	}

	public int getMaxTicketsAllowedToPurchase() {
		return this.maxTicketsAllowedToPurchase;
	}

	public void setMaxTicketsAllowedToPurchase(int maxTicketsAllowedToPurchase) {
		this.maxTicketsAllowedToPurchase = maxTicketsAllowedToPurchase;
	}

	public int getNumberOfBallsAvailable() {
		return this.numberOfBallsAvailable;
	}

	public void setNumberOfBallsAvailable(int numberOfBallsAvailable) {
		this.numberOfBallsAvailable = numberOfBallsAvailable;
	}

	public int getNumberOfBallsPicked() {
		return this.numberOfBallsPicked;
	}

	public void setNumberOfBallsPicked(int numberOfBallsPicked) {
		this.numberOfBallsPicked = numberOfBallsPicked;
	}

	public Date getPurchaseEndDate() {
		return this.purchaseEndDate;
	}

	public void setPurchaseEndDate(Date purchaseEndDate) {
		this.purchaseEndDate = purchaseEndDate;
	}

	public Date getPurchaseStartDate() {
		return this.purchaseStartDate;
	}

	public void setPurchaseStartDate(Date purchaseStartDate) {
		this.purchaseStartDate = purchaseStartDate;
	}

	public double getStudentWinningPercentage() {
		return this.studentWinningPercentage;
	}

	public void setStudentWinningPercentage(double studentWinningPercentage) {
		this.studentWinningPercentage = studentWinningPercentage;
	}

	public String getWinningNumber() {
		return this.winningNumber;
	}

	public void setWinningNumber(String winningNumber) {
		this.winningNumber = winningNumber;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}
	
	public String canPurchase() {
		Date now = new Date();
		if(now.after(purchaseStartDate) && now.before(purchaseEndDate))
			return "yes";
		else if(now.after(purchaseEndDate))
			return "late";
		else if(now.before(purchaseStartDate))
			return "early";
		return "no";
	}
	
	

}