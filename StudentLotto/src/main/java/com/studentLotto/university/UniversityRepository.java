package com.studentLotto.university;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.utilities.AccountActivation;

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

	public University findByName(String name) {
		try {
			return entityManager
					.createNamedQuery(University.FIND_BY_NAME, University.class)
					.setParameter("name", name).getSingleResult();
		} catch (PersistenceException e) {

			return null;
		}
	}

	public List<University> getUniversityList() {
		try {
			return entityManager.createNamedQuery(University.FIND_LIST,
					University.class).getResultList();
		} catch (PersistenceException e) {
			System.out.println("D");
			return null;
		}
	}

	public void update(University university) {
		University toUpdate = findByName(university.getName());
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
