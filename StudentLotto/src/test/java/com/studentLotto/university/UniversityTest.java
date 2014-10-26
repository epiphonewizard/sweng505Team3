package com.studentLotto.university;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.studentLotto.university.University;
import com.studentLotto.university.UniversityRepository;

public class UniversityTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
		University testUniversity = new University();
		UniversityRepository testUniversityRepository = new UniversityRepository();
		
		List<University> testUniversityList = testUniversityRepository.getUniversityList();
	}

}
