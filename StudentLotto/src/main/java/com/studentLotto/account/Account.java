package com.studentLotto.account;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@NamedQueries({
	@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email"),
	@NamedQuery(name = Account.FIND_BY_ROLE, query = "select a from Account a where a.role = :role"),
	@NamedQuery(name = Account.FIND_BY_ID, query = "select a from Account a where a.id = :id"),
	@NamedQuery(name = Account.UPDATE_BY_ID, query = "update Account a set a.role = :role where a.id = :id")



})
public class Account implements java.io.Serializable {

	public static final String FIND_BY_EMAIL = "Account.findByEmail";
	public static final String FIND_BY_ROLE = "Account.findByRole";
	public static final String FIND_BY_ID = "Account.findById";
	public static final String UPDATE_BY_ID = "Account.updateById";




	@Id
	@GeneratedValue
	private Long id; 

	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;
	
	@OneToOne(mappedBy = "account", cascade=CascadeType.ALL)
	private Person person;

	private String role;
	

	
    protected Account() {

	}
	
	public Account(String email, String password, String role, Person person) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.person = person;
	}
	
	public Account(Long id, String email, String role) {
		this.email = email;
	
		this.role = role;
		this.id = id; 
	}
	
	public Account(String email, String password, Person person, String role) {
		super();
		this.email = email;
		this.password = password;
		this.person = person;
		this.role = role;
	}
	
	public Account(String email, String password, String role) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
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
