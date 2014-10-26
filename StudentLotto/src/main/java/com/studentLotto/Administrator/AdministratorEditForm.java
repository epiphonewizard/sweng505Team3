package com.studentLotto.Administrator;
import org.hibernate.validator.constraints.NotBlank;

import com.studentLotto.account.Account;
import com.studentLotto.university.University;
import com.studentLotto.university.UniversityForm;



public class AdministratorEditForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private String email;
	
	@NotBlank(message = AdministratorEditForm.NOT_BLANK_MESSAGE)
	private String role;
	private Long id; 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}

	public AdministratorEditForm(Account account) {
		this.role = account.getRole();
		this.email = account.getEmail();
		this.id = account.getId(); 

	}
	
	public Account createAccount() {
		return new Account(getId(),  getEmail(), getRole());
	}
	

}
