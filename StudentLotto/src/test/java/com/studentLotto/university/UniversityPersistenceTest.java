package com.studentLotto.university;

import static org.junit.Assert.*;

import org.junit.Test;

public class UniversityPersistenceTest {

	@Test
	public void test() {
		University testUniversity = new University();
		UniversityRepository saveUniversity = new UniversityRepository();
		
		testUniversity.setName("Iowa State University");
		testUniversity.setAddressLine1("1750 BeardShear Hall");
		testUniversity.setCity("Aimes");
		testUniversity.setState("MO");
		testUniversity.setZip("50011");
		
		saveUniversity.save(testUniversity);
	}

}
