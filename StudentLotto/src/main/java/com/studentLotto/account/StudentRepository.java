package com.studentLotto.account;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class StudentRepository {
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	@Transactional
	public Student save(Student student) {
		entityManager.persist(student);
		return student;
	}
	
	public Student findByUniversityEmail(String email) {
		try {
			return entityManager.createNamedQuery(Student.FIND_BY_UNIVERSITY_EMAIL, Student.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
}
