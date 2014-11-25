package com.studentLotto.lottery;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.studentLotto.university.University;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
 
	private Integer winningNumber1; 
	private Integer winningNumber2; 
	private Integer winningNumber3; 
	private Integer winningNumber4; 
	private Integer winningNumber5; 
	private Integer winningNumber6;
	
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

	public Integer getWinningNumber1() {
		return winningNumber1;
	}

	public void setWinningNumber1(Integer winningNumber1) {
		this.winningNumber1 = winningNumber1;
	}

	public Integer getWinningNumber2() {
		return winningNumber2;
	}

	public void setWinningNumber2(Integer winningNumber2) {
		this.winningNumber2 = winningNumber2;
	}

	public Integer getWinningNumber3() {
		return winningNumber3;
	}

	public void setWinningNumber3(Integer winningNumber3) {
		this.winningNumber3 = winningNumber3;
	}

	public Integer getWinningNumber4() {
		return winningNumber4;
	}

	public void setWinningNumber4(Integer winningNumber4) {
		this.winningNumber4 = winningNumber4;
	}

	public Integer getWinningNumber5() {
		return winningNumber5;
	}

	public void setWinningNumber5(Integer winningNumber5) {
		this.winningNumber5 = winningNumber5;
	}

	public Integer getWinningNumber6() {
		return winningNumber6;
	}

	public void setWinningNumber6(Integer winningNumber6) {
		this.winningNumber6 = winningNumber6;
	}
	
	public void setWinningNumbers(Set<Integer> winningNumberSet) {
		List<Integer> winningNumberList=new ArrayList<>(winningNumberSet);
		if (winningNumberSet.size() == 4) {
			this.winningNumber1 = winningNumberList.get(0);
			this.winningNumber2 = winningNumberList.get(1);
			this.winningNumber3 = winningNumberList.get(2);
			this.winningNumber4 = winningNumberList.get(3);
		} else if (winningNumberSet.size() == 5) {
			this.winningNumber1 = winningNumberList.get(0);
			this.winningNumber2 = winningNumberList.get(1);
			this.winningNumber3 = winningNumberList.get(2);
			this.winningNumber4 = winningNumberList.get(3);
			this.winningNumber5 = winningNumberList.get(4);
		} else if (winningNumberSet.size() == 6) {
			this.winningNumber1 = winningNumberList.get(0);
			this.winningNumber2 = winningNumberList.get(1);
			this.winningNumber3 = winningNumberList.get(2);
			this.winningNumber4 = winningNumberList.get(3);
			this.winningNumber5 = winningNumberList.get(4);
			this.winningNumber6 = winningNumberList.get(5);
		}
	}
	
	public Set<Integer> getWinningNumbers() {
		Set<Integer> winningNumberSet= new HashSet<>();
		if (this.numberOfBallsPicked == 4) {
			winningNumberSet.add(this.winningNumber1);
			winningNumberSet.add(this.winningNumber2);
			winningNumberSet.add(this.winningNumber3);
			winningNumberSet.add(this.winningNumber4);
		} else if (this.numberOfBallsPicked == 5) {
			winningNumberSet.add(this.winningNumber1);
			winningNumberSet.add(this.winningNumber2);
			winningNumberSet.add(this.winningNumber3);
			winningNumberSet.add(this.winningNumber4);
			winningNumberSet.add(this.winningNumber5);
		} else if (this.numberOfBallsPicked == 6) {
			winningNumberSet.add(this.winningNumber1);
			winningNumberSet.add(this.winningNumber2);
			winningNumberSet.add(this.winningNumber3);
			winningNumberSet.add(this.winningNumber4);
			winningNumberSet.add(this.winningNumber5);
			winningNumberSet.add(this.winningNumber6);
		}
		return winningNumberSet;
	}

}