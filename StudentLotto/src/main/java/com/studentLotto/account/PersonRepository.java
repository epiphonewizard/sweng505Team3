package com.studentLotto.account;

import javax.persistence.*;
import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class PersonRepository {
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	@Transactional
	public Person save(Person person) {
		entityManager.persist(person);
		return person;
	}
	
	public Person findById(Long id) {
		try {
			return entityManager.createNamedQuery(Person.FIND_BY_ID, Person.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public Person findByName(String fname, String lname) {
		try {
			return entityManager.createNamedQuery(Person.FIND_BY_NAME, Person.class)
					.setParameter("fname", fname)
					.setParameter("lname", lname)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
}
