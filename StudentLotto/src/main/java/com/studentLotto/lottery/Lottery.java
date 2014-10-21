package com.studentLotto.lottery;

import java.io.Serializable;

import javax.persistence.*;

import com.studentLotto.account.Person;
import com.studentLotto.university.University;

import java.util.Date;


/**
 * The persistent class for the Lottery database table.
 * 
 */
@Entity
@NamedQuery(name="Lottery.findAll", query="SELECT l FROM Lottery l")
public class Lottery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date drawingDate;

	@Temporal(TemporalType.DATE)
	private Date purchaseEndDate;

	@Temporal(TemporalType.DATE)
	private Date purchaseStartDate;


	private String winningNumber;
	
	public Lottery() {
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

}