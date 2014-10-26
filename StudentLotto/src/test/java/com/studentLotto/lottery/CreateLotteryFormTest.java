package com.studentLotto.lottery;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.studentLotto.university.University;

import static org.junit.Assert.*;

public class CreateLotteryFormTest {

	@Test
	public void constructorTests(){
		CreateLotteryForm form = new CreateLotteryForm();
		assertEquals(Double.valueOf(0), form.getMaxWinnings());		
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
		
		Lottery lottery = form.newLottery(uni);
		assertEquals(uni, lottery.getUniversity());
		assertEquals(drawingDate, lottery.getDrawingDate());
		assertEquals(endDate, lottery.getPurchaseEndDate());
		assertEquals(BigDecimal.valueOf(0.0), lottery.getMaxWinnings());
	}
}
