package com.studentLotto.signup;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.*;

import com.studentLotto.account.Account;

@ScriptAssert(lang = "javascript", script = "_this.password.equals(_this.confirm)", message = "Password and Confirm fields must match")
public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	private String email;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String confirm;

	private String userType = "Student";

	//@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private Date dateOfBirth;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String firstName;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String lastName;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Pattern(regexp = "\\d?\\d{3}?\\d{7}", message = "Enter a valid phone number")
	private String phoneNumber;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String homeStreetAddress;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String homeCity;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String homeState = "PA";

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String homeCountry = "USA";

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String homeZip;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String mailStreetAddress;
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String mailCity;
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String mailState = "PA";
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String mailCountry = "USA";
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String mailZip;
	//@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private Long school;
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String major;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Account createAccount() {
		return new Account(getEmail(), getPassword(), "ROLE_USER");
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getHomeStreetAddress() {
		return homeStreetAddress;
	}

	public void setHomeStreetAddress(String homeStreetAddress) {
		this.homeStreetAddress = homeStreetAddress;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	public String getHomeState() {
		return homeState;
	}

	public void setHomeState(String homeState) {
		this.homeState = homeState;
	}

	public String getHomeCountry() {
		return homeCountry;
	}

	public void setHomeCountry(String homeCountry) {
		this.homeCountry = homeCountry;
	}

	public String getHomeZip() {
		return homeZip;
	}

	public void setHomeZip(String homeZip) {
		this.homeZip = homeZip;
	}

	public String getMailCity() {
		return mailCity;
	}

	public void setMailCity(String mailCity) {
		this.mailCity = mailCity;
	}

	public String getMailState() {
		return mailState;
	}

	public void setMailState(String mailState) {
		this.mailState = mailState;
	}

	public String getMailCountry() {
		return mailCountry;
	}

	public void setMailCountry(String mailCountry) {
		this.mailCountry = mailCountry;
	}

	public String getMailZip() {
		return mailZip;
	}

	public void setMailZip(String mailZip) {
		this.mailZip = mailZip;
	}

	public String getMailStreetAddress() {
		return mailStreetAddress;
	}

	public void setMailStreetAddress(String mailStreetAddress) {
		this.mailStreetAddress = mailStreetAddress;
	}

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	
}
