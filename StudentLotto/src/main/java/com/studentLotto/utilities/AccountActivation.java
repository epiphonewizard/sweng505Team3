package com.studentLotto.utilities;

import javax.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name = AccountActivation.FIND_BY_EMAIL, query = "select a from AccountActivation a where a.email = :email"),
		@NamedQuery(name = AccountActivation.FIND_BY_CODE, query = "select a from AccountActivation a where a.code = :code") })
public class AccountActivation implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_EMAIL = "AccountActivation.findByEmail";
	public static final String FIND_BY_CODE = "AccountActivation.findByCode";
	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String code;

	@Column(unique = false)
	private int activationStatus;

	
	public AccountActivation()
	{
		
	}
	public AccountActivation(String email, String code, int activationStatus) {
		this.email = email;
		this.code = code;
		this.activationStatus = activationStatus;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(int activationStatus) {
		this.activationStatus = activationStatus;
	}
	
	public String toString()
	{
		return "id: "+id+"\nemail:"+email+"\ncode:"+code+"\nactivationStatus:"+activationStatus;
	}

}
