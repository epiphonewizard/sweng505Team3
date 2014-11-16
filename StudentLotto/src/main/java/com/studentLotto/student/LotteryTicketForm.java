package com.studentLotto.student;

import javax.validation.constraints.AssertTrue;

public class LotteryTicketForm {
	@Override
	public String toString() {
		return "LotteryTicketForm [firstNumber=" + firstNumber
				+ ", secondNumber=" + secondNumber + ", thirdNumber="
				+ thirdNumber + ", fourthNumber=" + fourthNumber
				+ ", fifthNumber=" + fifthNumber + ", sixthNumber="
				+ sixthNumber + "]";
	}

	private int firstNumber;
	private int secondNumber;
	private int thirdNumber;
	private int fourthNumber;
	private int fifthNumber;
	private int sixthNumber;
	private boolean valid;

	public LotteryTicketForm() {

	}

	public LotteryTicketForm(int firstNumber, int secondNumber,
			int thirdNumber, int fourthNumber, int fifthNumber, int sixthNumber) {
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
		this.thirdNumber = thirdNumber;
		this.fourthNumber = fourthNumber;
		this.fifthNumber = fifthNumber;
		this.sixthNumber = sixthNumber;
	}

	/**
	 * @return the firstNumber
	 */
	public int getFirstNumber() {
		return firstNumber;
	}

	/**
	 * @param firstNumber
	 *            the firstNumber to set
	 */
	public void setFirstNumber(int firstNumber) {
		this.firstNumber = firstNumber;
	}

	/**
	 * @return the secondNumber
	 */
	public int getSecondNumber() {
		return secondNumber;
	}

	/**
	 * @param secondNumber
	 *            the secondNumber to set
	 */
	public void setSecondNumber(int secondNumber) {
		this.secondNumber = secondNumber;
	}

	/**
	 * @return the thirdNumber
	 */
	public int getThirdNumber() {
		return thirdNumber;
	}

	/**
	 * @param thirdNumber
	 *            the thirdNumber to set
	 */
	public void setThirdNumber(int thirdNumber) {
		this.thirdNumber = thirdNumber;
	}

	/**
	 * @return the fourthNumber
	 */
	public int getFourthNumber() {
		return fourthNumber;
	}

	/**
	 * @param fourthNumber
	 *            the fourthNumber to set
	 */
	public void setFourthNumber(int fourthNumber) {
		this.fourthNumber = fourthNumber;
	}

	/**
	 * @return the fifthNumber
	 */
	public int getFifthNumber() {
		return fifthNumber;
	}

	/**
	 * @param fifthNumber
	 *            the fifthNumber to set
	 */
	public void setFifthNumber(int fifthNumber) {
		this.fifthNumber = fifthNumber;
	}

	/**
	 * @return the sixthNumber
	 */
	public int getSixthNumber() {
		return sixthNumber;
	}

	/**
	 * @param sixthNumber
	 *            the sixthNumber to set
	 */
	public void setSixthNumber(int sixthNumber) {
		this.sixthNumber = sixthNumber;
	}

	@AssertTrue(message = "Can't choose the same number in the same ticket")
	private boolean isValid() {
		if (firstNumber == secondNumber) {
			valid = false;
		} else {
			valid = true;
		}

		return valid;
	}
}
