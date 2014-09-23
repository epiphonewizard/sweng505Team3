package com.studentLotto.account;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the student database table.
 * 
 */
@Entity
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String uAddressCity;

	private String uAddressLine1;

	private String uAddressLine2;

	private String uAddressState;

	private String uAddressZip;

	private String uEmailAddress;

	//bi-directional one-to-one association to Person
	@OneToOne(mappedBy="student")
	private Person person;

	public Student() {
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

}