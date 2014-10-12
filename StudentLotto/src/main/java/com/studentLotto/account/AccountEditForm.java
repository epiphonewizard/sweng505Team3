package com.studentLotto.account;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


public class AccountEditForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";
	
	public static DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

	private String userType = "Student";

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String dateOfBirth;

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String firstName;

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String lastName;

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	@Pattern(regexp = "\\d?\\d{3}?\\d{7}", message = "Enter a valid phone number")
	private String phoneNumber;

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String homeStreetAddress;

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String homeCity;

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String homeState = "PA";

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String homeZip;

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String mailStreetAddress;
	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String mailCity;
	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String mailState = "PA";

	@NotBlank(message = AccountEditForm.NOT_BLANK_MESSAGE)
	private String mailZip;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
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

	/**
	 * Create an AccountEditForm defaulting to the data found in a Person object
	 * @param person person who wishes to edit their account info
	 */
	public AccountEditForm(Person person) {
    	this.setDateOfBirth(df.format(person.getBirthdate()));
    	this.setFirstName(person.getFname());
    	this.setLastName(person.getLname());
    	this.setHomeCity(person.getPermAddressCity());
    	this.setHomeState(person.getPermAddressState());
    	this.setHomeStreetAddress(person.getPermAddressLine1());
    	this.setHomeZip(person.getPermAddressZip());
    	this.setPhoneNumber(person.getPhoneNumber());
    	Student student = person.getStudent();
    	if(student != null){
    		this.setMailCity(student.getUAddressCity());
    		this.setMailState(student.getUAddressState());
    		this.setMailStreetAddress(student.getUAddressLine1());
    		this.setMailZip(student.getUAddressZip());
    	}else{    		
    		this.setMailCity("N/A");
			this.setMailState("N/A");
			this.setMailStreetAddress("N/A");
			this.setMailZip("N/A");
    	}
	}
	
	/**
	 * Default Constructor
	 */
	public AccountEditForm(){
		
	}

	/**
	 * 
	 * @param current
	 * @return
	 */
	public Person getUpdatedPerson(Account current) {
		Person person = current.getPerson();
		if(person != null){
			person.setBirthdate(this.getDateOfBirthAsDate());
			person.setFname(this.getFirstName());
			person.setLname(this.getLastName());
			person.setPermAddressCity(this.getHomeCity());
			person.setPermAddressLine1(this.getHomeStreetAddress());
			person.setPermAddressState(this.getHomeState());
			person.setPermAddressZip(this.getHomeZip());
			person.setPhoneNumber(this.getPhoneNumber());
		}
		return person;
	}

	public Date getDateOfBirthAsDate() {
		 try {
			return df.parse(getDateOfBirth());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
	}

	public Student getUpdatedStudent(Student student) {
		if(student != null){		
			student.setUAddressCity(this.getMailCity());
			student.setUAddressLine1(this.getMailStreetAddress());
			student.setUAddressState(this.getMailState());
			student.setUAddressZip(this.getMailZip());
		}
		return student;
				
	}

	
}
