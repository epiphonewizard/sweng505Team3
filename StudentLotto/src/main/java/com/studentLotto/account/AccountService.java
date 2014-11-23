package com.studentLotto.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Transactional
	public void updateAccountRole(Long id, boolean isAdmin) {
		Account account = accountRepository.findById(id);
		if(isAdmin) {
			account.setRole("ROLE_ADMIN");
		} else {
			if(account.getPerson().isStudent()) {
				account.setRole("ROLE_STUDENT");
			} else {
				account.setRole("ROLE_DONOR");
			}
		}
		
	}
	
	@Transactional
	public void deleteUser(Long id) {
		Account account = accountRepository.findById(id);
		accountRepository.remove(account);
	}
}
