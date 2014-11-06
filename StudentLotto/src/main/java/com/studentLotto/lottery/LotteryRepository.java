package com.studentLotto.lottery;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.university.University;


@Repository
@Transactional(readOnly = true)
public class LotteryRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public Lottery save(Lottery lottery) {
		entityManager.persist(lottery);
		return lottery;
	}
	
	@Transactional
	public Lottery update(Lottery lottery){
		Lottery merged = entityManager.merge(lottery);
		entityManager.flush();
		return merged;
	}
	
	@Transactional
	public Lottery findUpcomingForUniversity(Long universityId) {
		try {
			return entityManager
					.createNamedQuery(Lottery.GET_UPCOMING_FOR_UNIVERSITY, Lottery.class)
					.setParameter("universityId", universityId)
					.setParameter("now", new Date())
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	@Transactional
	public List<Lottery> findAll() {
		try {
			return entityManager.createNamedQuery(Lottery.FIND_ALL, Lottery.class).getResultList();
		}catch(PersistenceException e){
			return null;
		}
	}
}
