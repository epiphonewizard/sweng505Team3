package com.studentLotto.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Transactional
	public void updateAccountRole(Long id, String role) {
		Account account = accountRepository.findById(id);
		account.setRole(role);
	}
	
	@Transactional
	public void deleteUser(Long id) {
		Account account = accountRepository.findById(id);
		accountRepository.remove(account);
	}
}
