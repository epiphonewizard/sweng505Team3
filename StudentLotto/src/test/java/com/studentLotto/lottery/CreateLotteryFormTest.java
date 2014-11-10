package com.studentLotto.lottery;

import java.util.Date;

import org.junit.Test;

import com.studentLotto.university.University;

import static org.junit.Assert.*;

public class CreateLotteryFormTest {

	@Test
	public void constructorTests(){
		CreateLotteryForm form = new CreateLotteryForm();
		assertEquals(0, form.getMaxWinnings(), 0.1);		
	}
	
	@Test
	public void createNewLottery(){
		CreateLotteryForm form = new CreateLotteryForm();
		Date drawingDate = new Date();
		Date startDate = new Date();
		Date endDate = new Date();
		form.setDrawingDate(drawingDate);
		form.setPurchaseStartDate(startDate);
		form.setPurchaseEndDate(endDate);
		University uni = new University();
		
		Lottery lottery = form.createLottery(uni);
		assertEquals(uni, lottery.getUniversity());
		assertEquals(drawingDate, lottery.getDrawingDate());
		assertEquals(endDate, lottery.getPurchaseEndDate());
		assertEquals(0.0, lottery.getMaxWinnings(), 0.1);
	}
}
