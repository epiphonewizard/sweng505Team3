package com.studentLotto.timedemail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class TimedEmailCodeRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public TimedEmailCode save(TimedEmailCode timedEmailCode){
		entityManager.persist(timedEmailCode);
		return timedEmailCode;
	}
	
	public TimedEmailCode findByGuid(String guid){
		try{
			return entityManager.createNamedQuery(TimedEmailCode.FIND_BY_GUID, TimedEmailCode.class)
				.setParameter("guid", guid)
				.getSingleResult();
		}catch(PersistenceException e){
			return null;
		}
	}
}
