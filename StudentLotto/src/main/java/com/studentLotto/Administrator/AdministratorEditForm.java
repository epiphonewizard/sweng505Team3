package com.studentLotto.Administrator;
import com.studentLotto.account.Account;



public class AdministratorEditForm {

	private String email;
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
public AdministratorEditForm(){
		
	}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}

	public AdministratorEditForm(Account account) {
		this.setEmail(account.getEmail());
		this.setRole(account.getRole());
		this.setId(account.getId());
	}
	

}
