package com.studentLotto.account;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
@Transactional
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
	
	@Transactional 
	public Account createNewPerson(Account account){
		Person person = new Person(new Date(),"","","","","","","", "");
		person.setAccount(account);
		person = entityManager.merge(person);
		account.setPerson(person);
		return entityManager.merge(account);
	}
	
	public Account findById(Long id) {
		return entityManager.find(Account.class, id);
	}
	
	public List<Account> findByRole(String role) {
		return entityManager
				.createNamedQuery(Account.FIND_BY_ROLE, Account.class)
				.setParameter("role", role).getResultList();
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
