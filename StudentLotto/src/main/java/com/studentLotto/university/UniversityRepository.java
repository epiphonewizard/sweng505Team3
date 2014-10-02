package com.studentLotto.university;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	/*public University findByUniversityId(int id) {
		try {
			return entityManager.createNamedQuery(University.FIND_BY_ID, University.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}*/
}
