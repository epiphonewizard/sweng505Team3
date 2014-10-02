package com.studentLotto.university;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.studentLotto.account.Account;

public class universityPersist {

	@Test
	public void test() {
		University testUniversity = new University();
		
		UniversityRepository universityRepository = new UniversityRepository();
		
		testUniversity.setAddressLine1("1500 Pennsylvania Avenue");
		testUniversity.setCity("Aimes");
		testUniversity.setName("Iowa State");
		testUniversity.setState("IA");
		testUniversity.setZip("50011");
		
		University saveUniversity = universityRepository.save(testUniversity);
		
		//Account account = accountRepository.save(signupForm.createAccount());
	}

}
