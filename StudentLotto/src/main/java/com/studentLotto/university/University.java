package com.studentLotto.university;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import com.studentLotto.lottery.Lottery;

/**
 * The persistent class for the University database table.
 */
@Entity
@NamedQueries({
		@NamedQuery(name = University.FIND_BY_NAME, query = "select u from University u where u.name = :name"),
		@NamedQuery(name = University.FIND_LIST, query = "select u from University u"), 
		@NamedQuery(name = University.FIND_BY_ID, query = "select u from University u where u.id = :id") 
		
})
public class University implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_LIST = "University.findList";
	public static final String FIND_BY_NAME = "University.findByName";
	public static final String FIND_BY_ID = "University.findById";

	@Id
	@GeneratedValue
	private Long id;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String name;

	@Column(columnDefinition = "blob")
	private byte[] picture;

	private String state;

	private String zip;

	// bi-directional many-to-one association to Lottery
	@OneToMany(mappedBy = "university", fetch = FetchType.EAGER)
	private List<Lottery> lotteries;

	public University() {
	}

	public University(Long id, String addressLine1, String addressLine2, String city,
			String name, String state, String zip) {
		super();
		this.id = id;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.name = name;
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

	public List<Lottery> getLotteries() {
		return this.lotteries;
	}

	public String toString() {
		return name + " " + addressLine1 + "  " + zip + "  " + state;
	}

	public void setLotteries(List<Lottery> lotteries) {
		this.lotteries = lotteries;
	}

	public Lottery addLottery(Lottery lottery) {
		getLotteries().add(lottery);
		lottery.setUniversity(this);

		return lottery;
	}

	public Lottery removeLottery(Lottery lottery) {
		getLotteries().remove(lottery);
		lottery.setUniversity(null);

		return lottery;
	}

}
