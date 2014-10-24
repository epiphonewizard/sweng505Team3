package com.studentLotto.Administrator;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import antlr.collections.List;

import com.studentLotto.account.Account;

@Repository
@Transactional(readOnly = true)
public class AdministratorRepository {

	@PersistenceContext
	private EntityManager entityManager;
	public <List> List findByRole(String role) {
		try {
			return (List) entityManager
					.createNamedQuery(Account.FIND_BY_ROLE, Account.class)
					.setParameter("role", role).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
	public Account findById(Long rid) {
		try {
			return entityManager
					.createNamedQuery(Account.FIND_BY_ID, Account.class)
					.setParameter("id", rid).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	public void updatebyId(Long rid, String role){
			
			entityManager.createNamedQuery(Account.UPDATE_BY_ID, Account.class)
				.setParameter("id", rid)
				.setParameter("role", role).executeUpdate();
	}	
}
