package com.studentLotto.account;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE p.id = :id"),
		@NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE p.fname = :fname AND p.lname = :lname") })
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_ID = "Person.findById";
	public static final String FIND_BY_NAME = "Person.findByName";

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date birthdate;

	private String fname;

	private String lname;

	private String permAddressCity;

	private String permAddressLine1;

	private String permAddressLine2;

	private String permAddressState;

	private String permAddressZip;

	private String phoneNumber;

	// bi-directional one-to-one association to Student
	@OneToOne(mappedBy="person")
	private Student student;

	@OneToOne(optional = false)
	@JoinColumn(name="accountId", referencedColumnName = "id")
	private Account account;

	public Person() {
	}


	

	public Long getId() {
		return this.id;
	}

	public Person(Date birthdate, String fname, String lname,
			String permAddressCity, String permAddressLine1,
			String permAddressLine2, String permAddressState,
			String permAddressZip, String phoneNumber) {
		super();
		this.birthdate = birthdate;
		this.fname = fname;
		this.lname = lname;
		this.permAddressCity = permAddressCity;
		this.permAddressLine1 = permAddressLine1;
		this.permAddressLine2 = permAddressLine2;
		this.permAddressState = permAddressState;
		this.permAddressZip = permAddressZip;
		this.phoneNumber = phoneNumber;
	}

	
	
	public Person(Date birthdate, String fname, String lname,
			String permAddressCity, String permAddressLine1,
			String permAddressLine2, String permAddressState,
			String permAddressZip, String phoneNumber, Student student) {
		super();
		this.birthdate = birthdate;
		this.fname = fname;
		this.lname = lname;
		this.permAddressCity = permAddressCity;
		this.permAddressLine1 = permAddressLine1;
		this.permAddressLine2 = permAddressLine2;
		this.permAddressState = permAddressState;
		this.permAddressZip = permAddressZip;
		this.phoneNumber = phoneNumber;
		this.student = student;
	}
	
	public Person(Date birthdate, String fname, String lname,
			String permAddressCity, String permAddressLine1,
			String permAddressLine2, String permAddressState,
			String permAddressZip, String phoneNumber, Account account) {
		super();
		this.birthdate = birthdate;
		this.fname = fname;
		this.lname = lname;
		this.permAddressCity = permAddressCity;
		this.permAddressLine1 = permAddressLine1;
		this.permAddressLine2 = permAddressLine2;
		this.permAddressState = permAddressState;
		this.permAddressZip = permAddressZip;
		this.phoneNumber = phoneNumber;
		this.account = account;
	}
	
	public Person(Date birthdate, String fname, String lname,
			String permAddressCity, String permAddressLine1,
			String permAddressLine2, String permAddressState,
			String permAddressZip, String phoneNumber, Account account, Student student) {
		super();
		this.birthdate = birthdate;
		this.fname = fname;
		this.lname = lname;
		this.permAddressCity = permAddressCity;
		this.permAddressLine1 = permAddressLine1;
		this.permAddressLine2 = permAddressLine2;
		this.permAddressState = permAddressState;
		this.permAddressZip = permAddressZip;
		this.phoneNumber = phoneNumber;
		this.account = account;
		this.student = student;
	}




	public void setId(Long id) {
		this.id = id;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPermAddressCity() {
		return this.permAddressCity;
	}

	public void setPermAddressCity(String permAddressCity) {
		this.permAddressCity = permAddressCity;
	}

	public String getPermAddressLine1() {
		return this.permAddressLine1;
	}

	public void setPermAddressLine1(String permAddressLine1) {
		this.permAddressLine1 = permAddressLine1;
	}

	public String getPermAddressLine2() {
		return this.permAddressLine2;
	}

	public void setPermAddressLine2(String permAddressLine2) {
		this.permAddressLine2 = permAddressLine2;
	}

	public String getPermAddressState() {
		return this.permAddressState;
	}

	public void setPermAddressState(String permAddressState) {
		this.permAddressState = permAddressState;
	}

	public String getPermAddressZip() {
		return this.permAddressZip;
	}

	public void setPermAddressZip(String permAddressZip) {
		this.permAddressZip = permAddressZip;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public boolean isStudent() {
		return this.student != null;
	}

}