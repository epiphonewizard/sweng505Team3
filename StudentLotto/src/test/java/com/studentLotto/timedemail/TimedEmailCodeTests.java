package com.studentLotto.timedemail;

import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimedEmailCodeTests {
	
	@Test
	public void testConstructor(){
		String email = "myEmail";
		String guid = "myGuid";
		Date dateSent = new Date();
		TimedEmailCode code = new TimedEmailCode(email, guid, dateSent);
		assertEquals(code.getEmail(), email);
		assertEquals(code.getGuid(), guid);
		assertEquals(code.getDateSent(), dateSent);
	}
	
	@Test
	public void safeToUsePositive(){
		String email = "myEmail";
		String guid = "myGuid";
		Date twentyThreeHoursAgo = new Date(System.currentTimeMillis() - (23 * 60 * 60 * 1000));
		TimedEmailCode code = new TimedEmailCode(email, guid, twentyThreeHoursAgo);
		assertEquals(code.safeToUse(email), true);
	}
	
	@Test
	public void safeToUseFailsForDifferentEmail(){		
		String email = "myEmail";
		String guid = "myGuid";
		Date twentyThreeHoursAgo = new Date(System.currentTimeMillis() - (23 * 60 * 60 * 1000));
		TimedEmailCode code = new TimedEmailCode(email, guid, twentyThreeHoursAgo);
		assertEquals(code.safeToUse("differentEmail"), false);		
	}
	
	@Test
	public void safeToUseFailsForOldDate(){		
		String email = "myEmail";
		String guid = "myGuid";
		Date twentyThreeHoursAgo = new Date(System.currentTimeMillis() - (25 * 60 * 60 * 1000));
		TimedEmailCode code = new TimedEmailCode(email, guid, twentyThreeHoursAgo);
		assertEquals(code.safeToUse(email), false);		
	}
}
