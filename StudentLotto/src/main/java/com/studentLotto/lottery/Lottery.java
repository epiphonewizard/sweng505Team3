package com.studentLotto.lottery;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

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
	@NamedQuery(name=Lottery.GET_UPCOMING_FOR_UNIVERSITY, query="SELECT l FROM Lottery l WHERE :now < l.drawingDate and universityId = :universityId")
	})
public class Lottery implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String GET_UPCOMING_FOR_UNIVERSITY = "Lottery.getUpcomingForUniversity";
	public static final String FIND_ALL = "Lottery.findAll";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date drawingDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date purchaseEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date purchaseStartDate;

	private String winningNumber;	

	private BigDecimal maxWinnings;
	
	
	public Lottery() {
	}
	
	public Lottery(Date drawingDate, Date purchaseStartDate, Date purchaseEndDate, BigDecimal maxWinnings, University university){
		setDrawingDate(drawingDate);
		setPurchaseStartDate(purchaseStartDate);
		setPurchaseEndDate(purchaseEndDate);
		setMaxWinnings(maxWinnings);
		setUniversity(university);
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


	public String getWinningNumber() {
		return this.winningNumber;
	}

	public void setWinningNumber(String winningNumber) {
		this.winningNumber = winningNumber;
	}
	
	@ManyToOne
	@JoinColumn(name="universityID", referencedColumnName="id")
	private University university;
	
	public University getUniversity(){
		return this.university;
	}
	
	public void setUniversity(University university){
		this.university = university;
	}

	public BigDecimal getMaxWinnings() {
		return maxWinnings;
	}

	public void setMaxWinnings(BigDecimal maxWinnings) {
		this.maxWinnings = maxWinnings;
	}

	public void addToMaxWinnings(double amount) {
		this.setMaxWinnings(BigDecimal.valueOf(this.getMaxWinnings().doubleValue() + amount));
	}

}