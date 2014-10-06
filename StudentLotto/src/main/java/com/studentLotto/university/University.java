package com.studentLotto.university;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the University database table.
 * 
 */
@Entity
@NamedQuery(name="University.findAll", query="SELECT u FROM University u")
public class University implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private Long lotteryId;

	private String name;

	//private Object picture;

	private String state;

	private String zip;

	public University(String name, String addressLine1, String addressLine2, String city, String state, String zip) {
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getLotteryId() {
		return this.lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public Object getPicture() {
		return this.picture;
	}

	public void setPicture(Object picture) {
		this.picture = picture;
	}*/

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}