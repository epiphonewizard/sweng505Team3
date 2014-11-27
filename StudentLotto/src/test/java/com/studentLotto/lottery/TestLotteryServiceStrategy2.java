package com.studentLotto.lottery;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.studentLotto.university.University;

public class TestLotteryServiceStrategy2 {

	@Test
	public void getMoneyRawCalculatedPerPersonCorrectedTest1() {
		ArrayList<Integer> matchCount = new ArrayList<Integer>(3);
		matchCount.add(1);
		matchCount.add(1);
		matchCount.add(2);
		ArrayList<Double> rawCalculatedMoneyPerPerson = new ArrayList<Double>(3);
		rawCalculatedMoneyPerPerson.add(20160.85);
		rawCalculatedMoneyPerPerson.add(4552.45);
		rawCalculatedMoneyPerPerson.add(650.35);

		double fullRide = 5000;
		LotteryService ls = new LotteryService();
		ArrayList<ArrayList<Double>> retObj = ls
				.getMoneyRawCalculatedPerPersonCorrected(
						rawCalculatedMoneyPerPerson, fullRide, matchCount);

		ArrayList<Double> remainder = retObj.get(0);
		ArrayList<Double> adjusted = retObj.get(1);
		ArrayList<Double> corrected = retObj.get(2);

		assertEquals(remainder.get(0), 15160.85, 0.001);
		assertEquals(remainder.get(1), 14713.3, 0.001);
		assertEquals(remainder.get(2), 3007, 0.001);

		assertEquals(adjusted.get(0), 20160.85, 0.001);
		assertEquals(adjusted.get(1), 19713.3, 0.001);
		assertEquals(adjusted.get(2), 8007, 0.001);

		assertEquals(corrected.get(0), 5000.0, 0.001);
		assertEquals(corrected.get(1), 5000.0, 0.001);
		assertEquals(corrected.get(2), 5000.0, 0.001);
	}

	@Test
	public void getMoneyRawCalculatedPerPersonCorrectedTest2() {
		ArrayList<Integer> matchCount = new ArrayList<Integer>(3);
		matchCount.add(1);
		matchCount.add(0);
		matchCount.add(2);
		ArrayList<Double> rawCalculatedMoneyPerPerson = new ArrayList<Double>(3);
		rawCalculatedMoneyPerPerson.add(24277.86667);
		rawCalculatedMoneyPerPerson.add(0.0);
		rawCalculatedMoneyPerPerson.add(867.0666667);

		double fullRide = 5000;
		LotteryService ls = new LotteryService();
		ArrayList<ArrayList<Double>> retObj = ls
				.getMoneyRawCalculatedPerPersonCorrected(
						rawCalculatedMoneyPerPerson, fullRide, matchCount);

		ArrayList<Double> remainder = retObj.get(0);
		ArrayList<Double> adjusted = retObj.get(1);
		ArrayList<Double> corrected = retObj.get(2);

		assertEquals(remainder.get(0), 19277.86667, 0.001);
		assertEquals(remainder.get(1), 0.0, 0.001);
		assertEquals(remainder.get(2), 0.0, 0.001);

		assertEquals(adjusted.get(0), 24277.86667, 0.001);
		assertEquals(adjusted.get(1), 0.0, 0.001);
		assertEquals(adjusted.get(2), 867.0666667, 0.001);

		assertEquals(corrected.get(0), 5000.0, 0.001);
		assertEquals(corrected.get(1), 0.0, 0.001);
		assertEquals(corrected.get(2), 867.0666667, 0.001);
	}

	@Test
	public void getMoneyRawCalculatedPerPersonCorrectedTest3() {
		ArrayList<Integer> matchCount = new ArrayList<Integer>(3);
		matchCount.add(1);
		matchCount.add(2);
		matchCount.add(0);
		ArrayList<Double> rawCalculatedMoneyPerPerson = new ArrayList<Double>(3);
		rawCalculatedMoneyPerPerson.add(19075.46667);
		rawCalculatedMoneyPerPerson.add(3468.266667);
		rawCalculatedMoneyPerPerson.add(0.0);

		double fullRide = 5000;
		LotteryService ls = new LotteryService();
		ArrayList<ArrayList<Double>> retObj = ls
				.getMoneyRawCalculatedPerPersonCorrected(
						rawCalculatedMoneyPerPerson, fullRide, matchCount);

		ArrayList<Double> remainder = retObj.get(0);
		ArrayList<Double> adjusted = retObj.get(1);
		ArrayList<Double> corrected = retObj.get(2);

		assertEquals(remainder.get(0), 14075.46667, 0.001);
		assertEquals(remainder.get(1), 5506.0, 0.001);
		assertEquals(remainder.get(2), 0.0, 0.001);

		assertEquals(adjusted.get(0), 19075.46667, 0.001);
		assertEquals(adjusted.get(1), 10506.0, 0.001);
		assertEquals(adjusted.get(2), 0.0, 0.001);

		assertEquals(corrected.get(0), 5000.0, 0.001);
		assertEquals(corrected.get(1), 5000.0, 0.001);
		assertEquals(corrected.get(2), 0.0, 0.001);
	}

	@Test
	public void convertTicketToSortedSetTest1() {
		LotteryService ls = new LotteryService();
		SortedSet<Integer> sorted = ls.convertTicketToSortedSet(20, 15, 18, 12,
				7, 22, 6);
		Integer[] compare = { 7, 12, 15, 18, 20, 22 };
		assertArrayEquals(sorted.toArray(), compare);

		sorted = ls.convertTicketToSortedSet(20, 15, 18, 12, 7, 22, 5);
		Integer[] compare2 = new Integer[] { 7, 12, 15, 18, 20 };
		assertArrayEquals(sorted.toArray(), compare2);

		sorted = ls.convertTicketToSortedSet(20, 15, 18, 12, 7, 22, 4);
		Integer[] compare3 = new Integer[] { 12, 15, 18, 20 };
		assertArrayEquals(sorted.toArray(), compare3);
	}

	@Test
	public void getMatchingNumberCountTest1() {
		LotteryService ls = new LotteryService();
		SortedSet<Integer> studentTicket = new TreeSet<Integer>();
		SortedSet<Integer> jackpotTicketNumbers = new TreeSet<Integer>();
		int matchingNum = -1;

		studentTicket.add(1);
		studentTicket.add(2);
		studentTicket.add(3);
		studentTicket.add(4);
		studentTicket.add(5);
		studentTicket.add(6);
		jackpotTicketNumbers.add(1);
		jackpotTicketNumbers.add(9);
		jackpotTicketNumbers.add(11);
		jackpotTicketNumbers.add(5);
		jackpotTicketNumbers.add(4);
		jackpotTicketNumbers.add(12);

		matchingNum = ls.getMatchingNumberCount(studentTicket,
				jackpotTicketNumbers);
		assertEquals(matchingNum, 3);

		// clear tickets & run another case
		studentTicket.clear();
		jackpotTicketNumbers.clear();
		studentTicket.add(1);
		studentTicket.add(2);
		studentTicket.add(3);
		studentTicket.add(4);
		studentTicket.add(5);
		studentTicket.add(6);
		jackpotTicketNumbers.add(6);
		jackpotTicketNumbers.add(5);
		jackpotTicketNumbers.add(3);
		jackpotTicketNumbers.add(9);
		jackpotTicketNumbers.add(20);
		jackpotTicketNumbers.add(1);

		matchingNum = ls.getMatchingNumberCount(studentTicket,
				jackpotTicketNumbers);
		assertEquals(matchingNum, 4);
	}

	@Test
	public void getWinnercategoryTest1() {
		LotteryService ls = new LotteryService();
		int category = -1;
		// getWinnercategory(int matchingNumberCount, int ballCount)
		category = ls.getWinnercategory(5, 5);
		assertEquals(category, 1);

		category = ls.getWinnercategory(4, 5);
		assertEquals(category, 2);

		category = ls.getWinnercategory(3, 5);
		assertEquals(category, 3);

		category = ls.getWinnercategory(2, 5);
		assertEquals(category, 4);

		category = ls.getWinnercategory(1, 5);
		assertEquals(category, 5);

		category = ls.getWinnercategory(1, 6);
		assertEquals(category, 6);

		category = ls.getWinnercategory(6, 6);
		assertEquals(category, 1);

		category = ls.getWinnercategory(4, 4);
		assertEquals(category, 1);

		category = ls.getWinnercategory(3, 4);
		assertEquals(category, 2);

		category = ls.getWinnercategory(2, 4);
		assertEquals(category, 3);

		category = ls.getWinnercategory(1, 4);
		assertEquals(category, 4);
	}

	@Test
	public void getAdjustedPercentagesTest1() {
		LotteryService ls = new LotteryService();
		ArrayList<Integer> in = new ArrayList<Integer>();
		ArrayList<Integer> matchingAdjusted = new ArrayList<Integer>();
		in.add(1);
		in.add(3);
		in.add(6);
		in.add(7);
		in.add(10);
		matchingAdjusted = ls.calculateMatchingTicketAdjusted(in);
		Integer[] compare = { 1, 4, 10, 17, 27 };
		assertArrayEquals(matchingAdjusted.toArray(), compare);

		in.clear();
		matchingAdjusted.clear();
		in.add(1);
		in.add(0);
		in.add(5);
		in.add(0);
		in.add(10);
		matchingAdjusted = ls.calculateMatchingTicketAdjusted(in);
		Integer[] compare2 = { 1, 1, 6, 6, 16 };
		assertArrayEquals(matchingAdjusted.toArray(), compare2);

		in.clear();
		matchingAdjusted.clear();
		in.add(0);
		in.add(0);
		in.add(5);
		in.add(22);
		in.add(1);
		matchingAdjusted = ls.calculateMatchingTicketAdjusted(in);
		Integer[] compare3 = { 0, 0, 5, 27, 28 };
		assertArrayEquals(matchingAdjusted.toArray(), compare3);

	}

	@Test
	public void calculateMatchingTicketAdjustedTest1() {
		LotteryService ls = new LotteryService();
		ArrayList<Double> output = new ArrayList<Double>();
		output = ls.getAdjustedPercentages(0, 2, 10, 50.0, 30.0, 20.0);
		assertEquals(output.get(0), 0.0, 0.01);
		assertEquals(output.get(1), 80.0, 0.01);
		assertEquals(output.get(2), 20.0, 0.01);

		output = ls.getAdjustedPercentages(0, 0, 10, 50.0, 30.0, 20.0);
		assertEquals(output.get(0), 0.0, 0.01);
		assertEquals(output.get(1), 0.0, 0.01);
		assertEquals(output.get(2), 100.0, 0.01);

		output = ls.getAdjustedPercentages(0, 10, 0, 50.0, 30.0, 20.0);
		assertEquals(output.get(0), 0.0, 0.01);
		assertEquals(output.get(1), 100.0, 0.01);
		assertEquals(output.get(2), 0.0, 0.01);

		output = ls.getAdjustedPercentages(10, 0, 0, 50.0, 30.0, 20.0);
		assertEquals(output.get(0), 100.0, 0.01);
		assertEquals(output.get(1), 0.0, 0.01);
		assertEquals(output.get(2), 0.0, 0.01);

		output = ls.getAdjustedPercentages(0, 0, 0, 50.0, 30.0, 20.0);
		assertEquals(output.get(0), 0.0, 0.01);
		assertEquals(output.get(1), 0.0, 0.01);
		assertEquals(output.get(2), 0.0, 0.01);

		output = ls.getAdjustedPercentages(10, 20, 0, 50.0, 30.0, 20.0);
		assertEquals(output.get(0), 50.0, 0.01);
		assertEquals(output.get(1), 50.0, 0.01);
		assertEquals(output.get(2), 0.0, 0.01);

		output = ls.getAdjustedPercentages(20, 0, 10, 50.0, 30.0, 20.0);
		assertEquals(output.get(0), 80.0, 0.01);
		assertEquals(output.get(1), 0.0, 0.01);
		assertEquals(output.get(2), 20.0, 0.01);
	}

	@Test
	public void getMoneyAllocatedPerGroupTest1() {
		LotteryService ls = new LotteryService();
		ArrayList<Double> adjustedPercentages = new ArrayList<Double>();
		adjustedPercentages.add(90.0);
		adjustedPercentages.add(0.0);
		adjustedPercentages.add(10.0);
		double lotteryPot = 14032;
		ArrayList<Double> output = ls.getMoneyAllocatedPerGroup(
				adjustedPercentages, lotteryPot);
		assertEquals(output.get(0), 12628.8, 0.001);
		assertEquals(output.get(1), 0.0, 0.001);
		assertEquals(output.get(2), 1403.2, 0.001);

		adjustedPercentages.clear();
		adjustedPercentages.add(60.0);
		adjustedPercentages.add(30.0);
		adjustedPercentages.add(10.0);
		lotteryPot = 14178;
		output = ls.getMoneyAllocatedPerGroup(adjustedPercentages, lotteryPot);
		assertEquals(output.get(0), 8506.8, 0.001);
		assertEquals(output.get(1), 4253.4, 0.001);
		assertEquals(output.get(2), 1417.8, 0.001);
	}

	@Test
	public void getMoneyAllocatedPerPersonPerGroupTest1() {
		LotteryService ls = new LotteryService();
		ArrayList<Double> moneyAllocatedPerGroup = new ArrayList<Double>();
		ArrayList<Integer> matchingTicketPerGroupAdjusted = new ArrayList<Integer>();
		moneyAllocatedPerGroup.add(8558.4);
		moneyAllocatedPerGroup.add(4279.2);
		moneyAllocatedPerGroup.add(1426.4);
		matchingTicketPerGroupAdjusted.add(23);
		matchingTicketPerGroupAdjusted.add(67);
		matchingTicketPerGroupAdjusted.add(122);
		ArrayList<Double> output = ls.getMoneyAllocatedPerPersonPerGroup(
				moneyAllocatedPerGroup, matchingTicketPerGroupAdjusted);
		assertEquals(output.get(0), 372.1043478, 0.001);
		assertEquals(output.get(1), 63.86865672, 0.001);
		assertEquals(output.get(2), 11.69180328, 0.001);

		moneyAllocatedPerGroup.clear();
		matchingTicketPerGroupAdjusted.clear();
		moneyAllocatedPerGroup.add(12758.4);
		moneyAllocatedPerGroup.add(0.0);
		moneyAllocatedPerGroup.add(1417.6);
		matchingTicketPerGroupAdjusted.add(23);
		matchingTicketPerGroupAdjusted.add(23);
		matchingTicketPerGroupAdjusted.add(78);
		output = ls.getMoneyAllocatedPerPersonPerGroup(moneyAllocatedPerGroup,
				matchingTicketPerGroupAdjusted);
		assertEquals(output.get(0), 554.7130435, 0.001);
		assertEquals(output.get(1), 0.0, 0.001);
		assertEquals(output.get(2), 18.17435897, 0.001);
	}

	@Test
	public void getMoneyRawCalculatedPerPersonTest1() {
		LotteryService ls = new LotteryService();
		ArrayList<Double> moneyAllocatedPerPersonPerGroup = new ArrayList<Double>();
		ArrayList<Double> adjustedPercentages = new ArrayList<Double>();
		adjustedPercentages.add(90.0);
		adjustedPercentages.add(0.0);
		adjustedPercentages.add(10.0);
		moneyAllocatedPerPersonPerGroup.add(554.7130435);
		moneyAllocatedPerPersonPerGroup.add(0.0);
		moneyAllocatedPerPersonPerGroup.add(18.17435897);
		ArrayList<Double> output = ls.getMoneyRawCalculatedPerPerson(
				moneyAllocatedPerPersonPerGroup, adjustedPercentages);
		System.out.println(output.toString());
		assertEquals(output.get(0), 572.8874025, 0.001);
		assertEquals(output.get(1), 0.0, 0.001);
		assertEquals(output.get(2), 18.17435897, 0.001);

		adjustedPercentages.clear();
		moneyAllocatedPerPersonPerGroup.clear();
		adjustedPercentages.add(60.0);
		adjustedPercentages.add(30.0);
		adjustedPercentages.add(10.0);
		moneyAllocatedPerPersonPerGroup.add(369.8608696);
		moneyAllocatedPerPersonPerGroup.add(177.225);
		moneyAllocatedPerPersonPerGroup.add(17.94683544);
		output = ls.getMoneyRawCalculatedPerPerson(
				moneyAllocatedPerPersonPerGroup, adjustedPercentages);
		assertEquals(output.get(0), 565.032705, 0.001);
		assertEquals(output.get(1), 195.1718354, 0.001);
		assertEquals(output.get(2), 17.94683544, 0.001);

		adjustedPercentages.clear();
		moneyAllocatedPerPersonPerGroup.clear();
		adjustedPercentages.add(60.0);
		adjustedPercentages.add(40.0);
		adjustedPercentages.add(0.0);
		moneyAllocatedPerPersonPerGroup.add(366.9913043);
		moneyAllocatedPerPersonPerGroup.add(234.4666667);
		moneyAllocatedPerPersonPerGroup.add(0.0);
		output = ls.getMoneyRawCalculatedPerPerson(
				moneyAllocatedPerPersonPerGroup, adjustedPercentages);
		assertEquals(output.get(0), 601.457971, 0.001);
		assertEquals(output.get(1), 234.4666667, 0.001);
		assertEquals(output.get(2), 0.0, 0.001);

	}
}
