package com.studentLotto.university;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.university.University;

@Repository
@Transactional(readOnly = true)
public class UniversityRepository{

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public University save(University university) {
		entityManager.persist(university);
		return university;
	}

	@Transactional
	public University findOne(Long id) {
		try {
			return entityManager.find(University.class, id);
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	@Transactional
	public University findByName(String name) {
		try {
			return entityManager
					.createNamedQuery(University.FIND_BY_NAME, University.class)
					.setParameter("name", name).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	@Transactional
	public University findById(Long id) {
		try {
			return entityManager
					.createNamedQuery(University.FIND_BY_ID, University.class)
					.setParameter("id", id).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	
	public List<University> getUniversityList() {
		try {
			return entityManager.createNamedQuery(University.FIND_LIST,
					University.class).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public List<University> getUniversityListForUpcomingLotteries() {
		try {
			return entityManager.createNamedQuery(University.GET_LIST_WITH_UPCOMING_LOTTERY,
					University.class)
					.setParameter("date", new Date()).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	@Transactional
	public void remove(University university) {
		entityManager.remove(entityManager.contains(university) ? university : entityManager.merge(university));
		entityManager.flush();
	}

	@Transactional
	public void update(University university) {
		University toUpdate = findById(university.getId());
		toUpdate.setZip(university.getZip());
		toUpdate.setAddressLine1(university.getAddressLine1());
		toUpdate.setAddressLine2(university.getAddressLine2());
		toUpdate.setCity(university.getCity());
		toUpdate.setName(university.getName());
		toUpdate.setState(university.getState());
		entityManager.merge(toUpdate);
		entityManager.flush();
	}
	
}
