package com.studentLotto.utilities;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class AccountActivationRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public AccountActivation save(AccountActivation accountActivation) {
		entityManager.persist(accountActivation);
		return accountActivation;
	}

	@Transactional
	public AccountActivation updateCode(AccountActivation accountActivation,
			String code) {
		accountActivation.setCode(code);
		entityManager.merge(accountActivation);
		entityManager.flush();
		return accountActivation;
	}

	public AccountActivation findByCode(String code) {
		try {
			return entityManager
					.createNamedQuery(AccountActivation.FIND_BY_CODE,
							AccountActivation.class).setParameter("code", code)
					.getSingleResult();
		} catch (PersistenceException e) {
		
			return null;
		}
	}

	public AccountActivation findByEmail(String email) {
		try {
			return entityManager
					.createNamedQuery(AccountActivation.FIND_BY_EMAIL,
							AccountActivation.class)
					.setParameter("email", email).getSingleResult();
		} catch (PersistenceException e) {
		
			return null;
		}
	}

	public String doesActivationCodeExist(String activationCode) {
		AccountActivation ac = findByCode(activationCode);
		if (ac == null) {
			return null;
		}
		return ac.getEmail();
	}

	public AccountActivation updateActivationStatus(
			AccountActivation accountActivation, int activationStatus) {
		accountActivation.setActivationStatus(activationStatus);
		accountActivation = entityManager.merge(accountActivation);
		entityManager.flush();
		System.out.println(accountActivation.toString());
		return accountActivation;

	}

}
