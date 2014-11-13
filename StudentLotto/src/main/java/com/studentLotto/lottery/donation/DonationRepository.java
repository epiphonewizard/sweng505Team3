package com.studentLotto.lottery.donation;
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
public class DonationRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public Donation save(Donation donation) {
		entityManager.persist(donation);
		return donation;
	}
	
	@Transactional
	public Donation update(Donation donation) {
		return entityManager.merge(donation);
	}
	
	@Transactional
	public Donation findById(Long id) {
		try {
			return entityManager.find(Donation.class, id);
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	@Transactional
	public List<Donation> findUnpaidForAccount(Long accountId) {
		try {
			return entityManager
					.createNamedQuery(Donation.GET_UNPAID_FOR_ACCOUNT, Donation.class)
					.setParameter("accountId", accountId).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	@Transactional
	public List<Donation> findForAccount(Long accountId) {
		try {
			return entityManager
					.createNamedQuery(Donation.GET_FOR_ACCOUNT, Donation.class)
					.setParameter("accountId", accountId).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
		
	@Transactional
	public void delete(Donation donation){
		entityManager.remove( entityManager.contains(donation) ? donation : entityManager.merge(donation));
	}

}
