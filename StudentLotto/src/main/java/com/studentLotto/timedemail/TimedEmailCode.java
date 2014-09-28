package com.studentLotto.timedemail;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@SuppressWarnings("serial")
@Entity
@NamedQuery(name = TimedEmailCode.FIND_BY_GUID, query = "select t from TimedEmailCode t where t.guid = :guid")
public class TimedEmailCode  implements java.io.Serializable {
	public static final String FIND_BY_GUID = "TimedEmailCode.find_by_guid";
	
	@Id 
	@GeneratedValue
	private Long id; 
	
	private String email;
	
	@Column(unique = true)
	private String guid;

	private Date dateSent;
	
	protected TimedEmailCode(){}
	
	public TimedEmailCode(String email, String guid, Date dateSent){
		this.email = email;
		this.guid = guid;
		this.dateSent = dateSent;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the dateSent
	 */
	public Date getDateSent() {
		return dateSent;
	}

	/**
	 * @param dateSent the dateSent to set
	 */
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	/**
	 * Determine if this code is still good to use for the action in question
	 * @param emailInQuestion email to verify against
	 */
	public boolean safeToUse(String emailInQuestion) {
		Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
		return email.equalsIgnoreCase(emailInQuestion) && (twentyFourHoursAgo.before(getDateSent()));
	}
	
	
}
