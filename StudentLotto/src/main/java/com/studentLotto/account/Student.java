package com.studentLotto.account;

import java.io.Serializable;

import javax.persistence.*;

import com.studentLotto.university.University;

/**
 * The persistent class for the student database table.
 * 
 */

@Entity
@NamedQueries({ @NamedQuery(name = Student.FIND_BY_EMAIL, query = "select a from Student a where a.uEmailAddress = :uEmailAddress"),
	@NamedQuery(name = Student.FIND_BY_ID, query = "select a from Student a where a.id = :id")})
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FIND_BY_EMAIL = "Student.findByEmail";
	public static final String FIND_BY_ID = "Student.findById";
	@Id
	@GeneratedValue
	private Long id;

	private String uAddressCity;

	private String uAddressLine1;

	private String uAddressLine2;

	private String uAddressState;

	private String uAddressZip;

	private String uEmailAddress;

	// bi-directional one-to-one association to Person
	@OneToOne(optional = false)
	@JoinColumn(name = "personId", referencedColumnName = "id")
	private Person person;

	@OneToOne(optional = false)
	@JoinColumn(name = "uId")
	private University university;

	public Student() {
	}

	public Student(String uAddressCity, String uAddressLine1,
			String uAddressLine2, String uAddressState, String uAddressZip,
			String uEmailAddress, University university, Person person) {
		super();
		this.uAddressCity = uAddressCity;
		this.uAddressLine1 = uAddressLine1;
		this.uAddressLine2 = uAddressLine2;
		this.uAddressState = uAddressState;
		this.uAddressZip = uAddressZip;
		this.uEmailAddress = uEmailAddress;
		this.person = person;
		this.university = university;
	}

	public Student(String uAddressCity, String uAddressLine1,
			String uAddressLine2, String uAddressState, String uAddressZip,
			String uEmailAddress, University university) {
		super();
		this.uAddressCity = uAddressCity;
		this.uAddressLine1 = uAddressLine1;
		this.uAddressLine2 = uAddressLine2;
		this.uAddressState = uAddressState;
		this.uAddressZip = uAddressZip;
		this.uEmailAddress = uEmailAddress;
		this.university = university;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUAddressCity() {
		return this.uAddressCity;
	}

	public void setUAddressCity(String uAddressCity) {
		this.uAddressCity = uAddressCity;
	}

	public String getUAddressLine1() {
		return this.uAddressLine1;
	}

	public void setUAddressLine1(String uAddressLine1) {
		this.uAddressLine1 = uAddressLine1;
	}

	public String getUAddressLine2() {
		return this.uAddressLine2;
	}

	public void setUAddressLine2(String uAddressLine2) {
		this.uAddressLine2 = uAddressLine2;
	}

	public String getUAddressState() {
		return this.uAddressState;
	}

	public void setUAddressState(String uAddressState) {
		this.uAddressState = uAddressState;
	}

	public String getUAddressZip() {
		return this.uAddressZip;
	}

	public void setUAddressZip(String uAddressZip) {
		this.uAddressZip = uAddressZip;
	}

	public String getUEmailAddress() {
		return this.uEmailAddress;
	}

	public void setUEmailAddress(String uEmailAddress) {
		this.uEmailAddress = uEmailAddress;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

}