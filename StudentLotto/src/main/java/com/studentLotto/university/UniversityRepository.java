package com.studentLotto.university;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.university.University;

@Repository
@Transactional(readOnly = true)
public class UniversityRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public University save(University university) {
		entityManager.persist(university);
		return university;
	}
	
	public University findByName(String name) {
		try {
			return entityManager
					.createNamedQuery(University.FIND_BY_NAME, University.class)
					.setParameter("name", name).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public University findByID(Long id) {
		try {
			return entityManager
					.createNamedQuery(University.FIND_BY_ID, University.class)
					.setParameter("id", id).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public List<University> findAllUniversities() {
		try {
			return entityManager
					.createNamedQuery(University.FIND_ALL_UNIVERSITIES, University.class)
					.getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
}
