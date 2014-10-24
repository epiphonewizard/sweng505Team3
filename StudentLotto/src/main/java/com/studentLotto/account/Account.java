package com.studentLotto.account;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email")
public class Account implements java.io.Serializable {

	public static final String FIND_BY_EMAIL = "Account.findByEmail";

	@Id
	@GeneratedValue
	private Long id; 

	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;
	
	@OneToOne(mappedBy = "account", cascade=CascadeType.ALL)
	private Person person;

	private String role = "ROLE_USER";
	

	
    protected Account() {

	}
	
	public Account(String email, String password, String role, Person person) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.person = person;
	}

	public Account(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}	
	
	public Account(String email, String password, Person person) {
		super();
		this.email = email;
		this.password = password;
		this.person = person;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
