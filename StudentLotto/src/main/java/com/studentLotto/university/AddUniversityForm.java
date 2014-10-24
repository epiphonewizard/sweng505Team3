package com.studentLotto.university;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.*;

public class AddUniversityForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = AddUniversityForm.NOT_BLANK_MESSAGE)
	private String name;

	@NotBlank(message = AddUniversityForm.NOT_BLANK_MESSAGE)
	private String addressLine1;

	private String addressLine2;
	
	@NotBlank(message = AddUniversityForm.NOT_BLANK_MESSAGE)
	private String addressCity;

	@NotBlank(message = AddUniversityForm.NOT_BLANK_MESSAGE)
	private String addressState;

	@NotBlank(message = AddUniversityForm.NOT_BLANK_MESSAGE)
	@Pattern(regexp = "\\d{5}", message = "Enter a valid zip code!")
	private String addressZip;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	
	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}

	public University createUniversity() {
		return new University(getAddressLine1(), getAddressLine2(), getAddressCity(), getName(), getAddressState(), getAddressZip());
	}
	
	public AddUniversityForm()
	{
		
	}

	public AddUniversityForm(University university) {
		this.name = university.getName();
		this.addressLine1 = university.getAddressLine1();;
		this.addressLine2 = university.getAddressLine2();
		this.addressCity = university.getCity();
		this.addressState = university.getState();
		this.addressZip = university.getZip();
	}
}
