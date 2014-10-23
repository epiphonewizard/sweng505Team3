package com.studentLotto.lottery;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

import com.studentLotto.signup.SignupForm;
@ScriptAssert.List({
	@ScriptAssert(lang = "javascript", script = "_this.purchaseStartDate.before(_this.purchaseEndDate)", message = "The Purchase start date must be before the end date."),
	@ScriptAssert(lang = "javascript", script = "_this.purchaseEndDate.before(_this.drawingDate)", message = "The Purchase period must end before the drawing begins.")
})
public class CreateLotteryForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	public CreateLotteryForm(){
		this.setMaxWinnings(0.0);
	}
	
	@NotNull(message = CreateLotteryForm.NOT_BLANK_MESSAGE)
	private Date drawingDate;
	
	@NotNull(message = CreateLotteryForm.NOT_BLANK_MESSAGE)
	private Date purchaseEndDate;
	
	@NotNull(message = CreateLotteryForm.NOT_BLANK_MESSAGE)
	private Date purchaseStartDate;
	
	private Double maxWinnings;
	
	private long universityID;
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
	public long getUniversityID() {
		return universityID;
	}
	public void setUniversityID(long universityID) {
		this.universityID = universityID;
	}
	
	
}
