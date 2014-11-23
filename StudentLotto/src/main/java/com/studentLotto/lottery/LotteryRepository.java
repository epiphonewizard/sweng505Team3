package com.studentLotto.lottery;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.lottery.donation.Donation;
import com.studentLotto.student.LotteryTicket;
import com.studentLotto.university.University;


@Repository
@Transactional
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
					.setMaxResults(1)
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
	
	@Transactional
	public Lottery findOne(int id) {
		try {
			return entityManager.find(Lottery.class, id);
		}catch(PersistenceException e){
			return null;
		}
	}
	
	@Transactional
	public void remove(Lottery lottery) {
		try {
			entityManager.remove(lottery);
		}catch(PersistenceException e){
		
		}
	}
	
	@Transactional
	public List<Donation> getCompletedDonationsForLottery(Lottery lottery){
		try {
			return entityManager.createNamedQuery(Donation.GET_COMPLETED_FOR_LOTTERY, Donation.class).setParameter("lotteryId", lottery.getId()).getResultList();
		}catch(PersistenceException e){
			return null;
		}
	}
	
	@Transactional
	public List<LotteryTicket> getCompletedTicketsForLottery(Lottery lottery){
		try {
			return entityManager.createNamedQuery(LotteryTicket.FIND_TICKETS_FOR_LOTTERY, LotteryTicket.class).setParameter("lotteryId", lottery.getId()).getResultList();
		}catch(PersistenceException e){
			return null;
		}
	}
	
	public double calculateLotteryWinnings(Lottery lottery){
		double total = 0.0;
		total += Donation.getTotal(getCompletedDonationsForLottery(lottery));
		total += LotteryTicket.getTotal(getCompletedTicketsForLottery(lottery));
		return total;
	}
	
	
}
