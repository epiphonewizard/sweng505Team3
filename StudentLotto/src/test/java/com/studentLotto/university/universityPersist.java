package com.studentLotto.university;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.studentLotto.account.Account;

public class universityPersist {

	@Test
	public void persistTest() {
		UniversityRepository universityRepository = new UniversityRepository();
		AddUniversityForm addUniversityForm = new AddUniversityForm();

		addUniversityForm.setName("CodeTest");
		addUniversityForm.setAddressLine1("test");
		addUniversityForm.setAddressCity("test");
		addUniversityForm.setAddressState("MO");
		addUniversityForm.setAddressZip("63303");
		
		University university = universityRepository.save(addUniversityForm.createUniversity());

	}
	
	@Test
	public void createUniversityTest() {
		University university = null;
	}

}
