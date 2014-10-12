package com.studentLotto.account;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;


public class AccountEditFormTests {
	@Test
	public void constructorForStudent(){
		Student student = new Student(
				"city", "address1", "address2","state", "zip", "email", null, null);
		Person person = new Person(new Date(),
				"firstName", "lastName", "city", "address1","address2", "state",
				"zip", "phone", student);
		AccountEditForm form = new AccountEditForm(person);
		assertEquals(AccountEditForm.df.format(person.getBirthdate()), form.getDateOfBirth());
		assertEquals(person.getFname(), form.getFirstName());
		assertEquals(person.getLname(), form.getLastName());
		assertEquals(person.getPermAddressCity(), form.getHomeCity());
		assertEquals(person.getPermAddressLine1(), form.getHomeStreetAddress());
		assertEquals(person.getPermAddressState(), form.getHomeState());
		assertEquals(person.getPermAddressZip(), form.getHomeZip());
		assertEquals(person.getPhoneNumber(), form.getPhoneNumber());
		
		assertEquals(student.getUAddressCity(), form.getMailCity());
		assertEquals(student.getUAddressLine1(), form.getMailStreetAddress());
		assertEquals(student.getUAddressState(), form.getMailState());
		assertEquals(student.getUAddressZip(), form.getMailZip());
	}
	
	@Test
	public void constructorForDonator(){
		Person person = new Person(new Date(),
				"firstName", "lastName", "city", "address1","address2", "state",
				"zip", "phone");
		AccountEditForm form = new AccountEditForm(person);
		assertEquals(AccountEditForm.df.format(person.getBirthdate()), form.getDateOfBirth());
		assertEquals(person.getFname(), form.getFirstName());
		assertEquals(person.getLname(), form.getLastName());
		assertEquals(person.getPermAddressCity(), form.getHomeCity());
		assertEquals(person.getPermAddressLine1(), form.getHomeStreetAddress());
		assertEquals(person.getPermAddressState(), form.getHomeState());
		assertEquals(person.getPermAddressZip(), form.getHomeZip());
		assertEquals(person.getPhoneNumber(), form.getPhoneNumber());
		
		assertEquals("N/A", form.getMailCity());
		assertEquals("N/A", form.getMailStreetAddress());
		assertEquals("N/A", form.getMailState());
		assertEquals("N/A", form.getMailZip());
	}
	
	@Test
	public void getDateOfBirthAsDate() throws ParseException{
		AccountEditForm form = new AccountEditForm();
		form.setDateOfBirth("06/15/1985");
		
		Date birthDate = form.getDateOfBirthAsDate();		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		assertEquals(df.parse("1985-06-15"), birthDate);
	}
	
	@Test
	public void getUpdatedPerson(){
		AccountEditForm form = new AccountEditForm();
		form.setDateOfBirth("06/15/1985");
    	form.setFirstName("Ryan");
    	form.setLastName("Hicke");
    	form.setHomeCity("Portland");
    	form.setHomeState("OR");
    	form.setHomeStreetAddress("2186 NW Naito AVE");
    	form.setHomeZip("97210");
    	form.setPhoneNumber("555555555");
    	Account account = new Account();
    	account.setPerson(new Person());
    	Person person = form.getUpdatedPerson(account);
    	assertEquals("Ryan", person.getFname());
    	assertEquals("Hicke", person.getLname());
    	assertEquals("Portland", person.getPermAddressCity());
    	assertEquals("OR", person.getPermAddressState());
    	assertEquals("2186 NW Naito AVE", person.getPermAddressLine1());
    	assertEquals("97210", person.getPermAddressZip());
    	assertEquals("555555555", person.getPhoneNumber());
    	
    	

	}
	
	@Test 
	public void getUpdatedStudent(){
		AccountEditForm form = new AccountEditForm();
		form.setMailCity("city");
		form.setMailState("state");
		form.setMailStreetAddress("address");
		form.setMailZip("zip");
		form.setDateOfBirth("06/15/1985");
		Student student = form.getUpdatedStudent(new Student());
		assertEquals("city", student.getUAddressCity());
		assertEquals("state", student.getUAddressState());
		assertEquals("address", student.getUAddressLine1());
		assertEquals("zip", student.getUAddressZip());
	}
}
