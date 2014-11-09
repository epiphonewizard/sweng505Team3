package com.studentLotto.student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.utilities.AccountActivation;

@Repository
@Transactional(readOnly = true)
public class PurchaseTicketRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public LotteryTicket save(LotteryTicket lotteryTicket) {
		entityManager.persist(lotteryTicket);
		return lotteryTicket;
	}



}
