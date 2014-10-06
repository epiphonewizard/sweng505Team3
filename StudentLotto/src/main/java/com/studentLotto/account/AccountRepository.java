package com.studentLotto.account;

import javax.persistence.*;
import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
@Transactional(readOnly = true)
public class AccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private PasswordEncoder passwordEncoder;

	@Transactional
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		entityManager.persist(account);
		return account;
	}
	
	@Transactional
	public void remove(Account account) {
		entityManager.remove(account);
	}
	
	@Transactional
	public Account saveAndFlush(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		entityManager.persist(account);
		entityManager.flush();
		return account;
	}

	@Transactional
	public Account changePassword(Account account, String password){
		account.setPassword(passwordEncoder.encode(password));
		entityManager.merge(account);
		return account;
	}
	
	public Account findByEmail(String email) {
		try {
			return entityManager
					.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
					.setParameter("email", email).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

}
