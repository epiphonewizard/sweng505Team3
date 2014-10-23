package com.studentLotto.lottery;

import java.util.Date;

public class CreateLotteryForm {
	private Date drawingDate;
	private Date purchaseEndDate;
	private Date purchaseStartDate;
	private Double maxWinnings;
	private int universityID;
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
	public Double getMaxWinnings() {
		return maxWinnings;
	}
	public void setMaxWinnings(Double maxWinnings) {
		this.maxWinnings = maxWinnings;
	}
	public int getUniversityID() {
		return universityID;
	}
	public void setUniversityID(int universityID) {
		this.universityID = universityID;
	}
	
}
