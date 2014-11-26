package com.studentLotto.lottery;


import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.format.annotation.DateTimeFormat;

@ScriptAssert.List({
	@ScriptAssert(lang = "javascript", script = "_this.purchaseStartDate != null && _this.purchaseEndDate != null", message = "The Purchase start date must be before the end date."),
	@ScriptAssert(lang = "javascript", script = "_this.drawingDate != null && _this.purchaseEndDate != null && _this.purchaseEndDate.before(_this.drawingDate)", message = "The Purchase period must end before the drawing begins.")
})

public class EditLotteryForm {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	private int id;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	@NotNull(message = EditLotteryForm.NOT_BLANK_MESSAGE)
	private Date drawingDate;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	@NotNull(message = EditLotteryForm.NOT_BLANK_MESSAGE)
	private Date purchaseEndDate;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	@NotNull(message = EditLotteryForm.NOT_BLANK_MESSAGE)
	private Date purchaseStartDate;
	
	private Boolean fullMatchGuaranteed;

	@NotNull(message = EditLotteryForm.NOT_BLANK_MESSAGE)
	private double lotteryTicketCost;

	private int maxTicketsAllowedToPurchase;

	@NotNull(message = EditLotteryForm.NOT_BLANK_MESSAGE)
	private int numberOfBallsAvailable;

	@NotNull(message = EditLotteryForm.NOT_BLANK_MESSAGE)
	private int numberOfBallsPicked;

	@NotNull(message = EditLotteryForm.NOT_BLANK_MESSAGE)
	private double studentWinningPercentage;

	private long universityID;
	
	@NotNull(message = EditLotteryForm.NOT_BLANK_MESSAGE)
	private double maxStudentWinnings;
	
	public EditLotteryForm(){}
	
	public EditLotteryForm(Lottery lottery) {
		super();
		this.id = lottery.getId();
		this.drawingDate = lottery.getDrawingDate();
		this.purchaseEndDate = lottery.getPurchaseEndDate();
		this.purchaseStartDate = lottery.getPurchaseStartDate();
		this.fullMatchGuaranteed = lottery.getFullMatchGuaranteed();
		this.lotteryTicketCost = lottery.getLotteryTicketCost();
		this.maxTicketsAllowedToPurchase = lottery.getMaxTicketsAllowedToPurchase();
		this.numberOfBallsAvailable = lottery.getNumberOfBallsAvailable();
		this.numberOfBallsPicked = lottery.getNumberOfBallsPicked();
		this.studentWinningPercentage = lottery.getStudentWinningPercentage();
		this.universityID = lottery.getUniversity().getId();
		this.maxStudentWinnings = lottery.getMaxStudentWinnings();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDrawingDate() {
		return drawingDate;
	}
	public void setDrawingDate(Date drawingDate) {
		this.drawingDate = drawingDate;
	}
	public Date getPurchaseStartDate() {
		return purchaseStartDate;
	}
	public void setPurchaseStartDate(Date purchaseStartDate) {
		this.purchaseStartDate = purchaseStartDate;
	}
	public Date getPurchaseEndDate() {
		return purchaseEndDate;
	}
	public void setPurchaseEndDate(Date purchaseEndDate) {
		this.purchaseEndDate = purchaseEndDate;
	}
	public long getUniversityID() {
		return universityID;
	}
	public void setUniversityID(long universityID) {
		this.universityID = universityID;
	}
	public Boolean getFullMatchGuaranteed() {
		return fullMatchGuaranteed;
	}
	public void setFullMatchGuaranteed(Boolean fullMatchGuaranteed) {
		this.fullMatchGuaranteed = fullMatchGuaranteed;
	}
	public double getLotteryTicketCost() {
		return lotteryTicketCost;
	}
	public void setLotteryTicketCost(double lotteryTicketCost) {
		this.lotteryTicketCost = lotteryTicketCost;
	}
	public int getMaxTicketsAllowedToPurchase() {
		return maxTicketsAllowedToPurchase;
	}
	public void setMaxTicketsAllowedToPurchase(int maxTicketsAllowedToPurchase) {
		this.maxTicketsAllowedToPurchase = maxTicketsAllowedToPurchase;
	}
	public int getNumberOfBallsAvailable() {
		return numberOfBallsAvailable;
	}
	public void setNumberOfBallsAvailable(int numberOfBallsAvailable) {
		this.numberOfBallsAvailable = numberOfBallsAvailable;
	}
	public int getNumberOfBallsPicked() {
		return numberOfBallsPicked;
	}
	public void setNumberOfBallsPicked(int numberOfBallsPicked) {
		this.numberOfBallsPicked = numberOfBallsPicked;
	}
	public double getStudentWinningPercentage() {
		return studentWinningPercentage;
	}
	public void setStudentWinningPercentage(double studentWinningPercentage) {
		this.studentWinningPercentage = studentWinningPercentage;
	}

	public double getMaxStudentWinnings() {
		return maxStudentWinnings;
	}

	public void setMaxStudentWinnings(double maxStudentWinnings) {
		this.maxStudentWinnings = maxStudentWinnings;
	}
	
}
