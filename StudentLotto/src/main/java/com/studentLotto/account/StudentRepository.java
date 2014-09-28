package com.studentLotto.account;

import javax.persistence.*;
import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

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
