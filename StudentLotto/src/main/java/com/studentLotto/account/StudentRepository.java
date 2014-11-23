package com.studentLotto.account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.student.LotteryTicket;

@Repository
@Transactional(readOnly = true)
public class StudentRepository {
	@PersistenceContext
	private EntityManager entityManager;

	public Student findByUEmailAddress(String email) {

		try {
			return entityManager
					.createNamedQuery(Student.FIND_BY_EMAIL, Student.class)
					.setParameter("uEmailAddress", email).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public Student findWinnerEmail(int id){
		
		return entityManager
					.createNamedQuery(Student.FIND_BY_ID, Student.class)
					.setParameter("id", id).getSingleResult();
					
		}

	@Transactional
	public Student save(Student student) {
		entityManager.persist(student);
		return student;
	}
	
	@Transactional
	public Student update(Student student) {
		entityManager.merge(student);
		return student;
	}
}
