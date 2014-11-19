package com.studentLotto.student;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.donation.Donation;
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

	@Transactional
	public LotteryTicket update(LotteryTicket lotteryTicket) {
		return entityManager.merge(lotteryTicket);
	}

	@Transactional
	public LotteryTicket findById(Long id) {
		try {
			return entityManager.find(LotteryTicket.class, id);
		} catch (PersistenceException e) {
			return null;
		}
	}

	@Transactional
	public int findStudentPurchasedticketForLotteryCount(long studentId,
			int lotteryId) {

		try {
			return entityManager
					.createNamedQuery(
							LotteryTicket.FIND_STUDENT_RESERVED_TICEKT_FOR_LOTTERY,
							LotteryTicket.class)
					.setParameter("lotteryId", lotteryId)
					.setParameter("studentId", studentId).getResultList()
					.size();
		} catch (PersistenceException e) {
			return 0;
		}

	}

	@Transactional
	public List<LotteryTicket> findStudentUnpaidTicketForUpcomingLottery(
			long studentId, int lotteryId) {

		try {
			return entityManager
					.createNamedQuery(
							LotteryTicket.FIND_STUDENT_UNPAID_TICEKT_FOR_UPCOMING_LOTTERY,
							LotteryTicket.class)
					.setParameter("lotteryId", lotteryId)
					.setParameter("studentId", studentId).getResultList();
		} catch (PersistenceException e) {
			return null;
		}

	}
	
	
	@Transactional
	public void delete(LotteryTicket ticket){
		entityManager.remove( entityManager.contains(ticket) ? ticket : entityManager.merge(ticket));
	}
	
	@Transactional
	public List<LotteryTicket> findStudentTicketsForUpcomingLottery(long studentId,
			int lotteryId) {
		try {
			return entityManager
					.createNamedQuery(
							LotteryTicket.FIND_STUDENT_RESERVED_TICEKT_FOR_LOTTERY,
							LotteryTicket.class)
					.setParameter("lotteryId", lotteryId)
					.setParameter("studentId", studentId).getResultList();
		} catch (PersistenceException e) {
			return null;
		}

	}

}
