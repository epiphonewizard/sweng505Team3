package com.studentLotto.university;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;


/**
 * The persistent class for the University database table.
 * 
 */
@Entity
@NamedQuery(name="University.findById", query="SELECT u FROM University u WHERE u.ID = :id")
public class University implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_ID = "University.findById";
	
	@Id
	private String id;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private BigInteger lotteryId;

	private String name;

	@Lob
	private byte[] picture;

	private String state;

	private String zip;

	public University() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

	public BigInteger getLotteryId() {
		return this.lotteryId;
	}

	public void setLotteryId(BigInteger lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

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