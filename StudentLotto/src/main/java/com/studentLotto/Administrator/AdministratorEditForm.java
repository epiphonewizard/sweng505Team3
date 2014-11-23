package com.studentLotto.Administrator;

import com.studentLotto.account.Account;

public class AdministratorEditForm {

	private String email;

	private Long id;
	
	private boolean admin;

	public AdministratorEditForm() {
	}
	
	public AdministratorEditForm(Account account) {
		this.id = account.getId();
	    this.email = account.getEmail();
	    if(account.getRole().equals("ROLE_ADMIN")) {
	    	this.admin = true;
	    } else {
	    	this.admin = false;
	    }
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
