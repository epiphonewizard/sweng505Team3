package com.studentLotto.lottery;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import com.studentLotto.student.LotteryTicket;
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
	public void getMoneyRawCalculatedPerPersonCorrectedTest4() {
		ArrayList<Integer> matchCount = new ArrayList<Integer>(3);
		matchCount.add(0);
		matchCount.add(0);
		matchCount.add(3);
		ArrayList<Double> rawCalculatedMoneyPerPerson = new ArrayList<Double>(3);
		rawCalculatedMoneyPerPerson.add(0.0);
		rawCalculatedMoneyPerPerson.add(0.0);
		rawCalculatedMoneyPerPerson.add(7.2);

		double fullRide = 2000;
		LotteryService ls = new LotteryService();
		ArrayList<ArrayList<Double>> retObj = ls
				.getMoneyRawCalculatedPerPersonCorrected(
						rawCalculatedMoneyPerPerson, fullRide, matchCount);

		ArrayList<Double> remainder = retObj.get(0);
		ArrayList<Double> adjusted = retObj.get(1);
		ArrayList<Double> corrected = retObj.get(2);

		assertEquals(remainder.get(0), 0.0, 0.001);
		assertEquals(remainder.get(1), 0.0, 0.001);
		assertEquals(remainder.get(2), 0.0, 0.001);

		assertEquals(adjusted.get(0), 0.0, 0.001);
		assertEquals(adjusted.get(1), 0.0, 0.001);
		assertEquals(adjusted.get(2), 7.2, 0.001);

		assertEquals(corrected.get(0), 0.0, 0.001);
		assertEquals(corrected.get(1), 0.0, 0.001);
		assertEquals(corrected.get(2), 7.2, 0.001);
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

	@Test
	public void getMinPersonPayoutTest1() {
		LotteryService ls = new LotteryService();
		ArrayList<Double> corrected = new ArrayList<Double>();
		// the algorithm considers only the top 3 categories and ignores the 2.0
		corrected.add(100.0);
		corrected.add(50.0);
		corrected.add(600.0);
		corrected.add(2.0);
		ArrayList<Integer> matchingTicketPerGroup = new ArrayList<Integer>();
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		double min = 0.0;
		min = ls.getMinPersonPayout(corrected, matchingTicketPerGroup);
		assertEquals(min, 50.0, 0.001);

		corrected.clear();
		matchingTicketPerGroup.clear();
		corrected.add(100.0);
		corrected.add(50.0);
		corrected.add(30.0);
		corrected.add(2.0);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		min = ls.getMinPersonPayout(corrected, matchingTicketPerGroup);
		assertEquals(min, 30.0, 0.001);

		corrected.clear();
		matchingTicketPerGroup.clear();
		corrected.add(1.0);
		corrected.add(50.0);
		corrected.add(30.0);
		corrected.add(2.0);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		min = ls.getMinPersonPayout(corrected, matchingTicketPerGroup);
		assertEquals(min, 1.0, 0.001);

		corrected.clear();
		matchingTicketPerGroup.clear();
		corrected.add(30.0);
		corrected.add(30.0);
		corrected.add(20.0);
		corrected.add(2.0);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		min = ls.getMinPersonPayout(corrected, matchingTicketPerGroup);
		assertEquals(min, 20.0, 0.001);

		corrected.clear();
		matchingTicketPerGroup.clear();
		corrected.add(30.0);
		corrected.add(20.0);
		corrected.add(30.0);
		corrected.add(2.0);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		min = ls.getMinPersonPayout(corrected, matchingTicketPerGroup);
		assertEquals(min, 20.0, 0.001);

		corrected.clear();
		matchingTicketPerGroup.clear();
		corrected.add(30.0);
		corrected.add(30.0);
		corrected.add(30.0);
		corrected.add(2.0);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		matchingTicketPerGroup.add(1);
		min = ls.getMinPersonPayout(corrected, matchingTicketPerGroup);
		assertEquals(min, 30.0, 0.001);

	}

	@Test
	public void reDistributeRemainingDollarsTest1() {
		LotteryService ls = new LotteryService();
		double dollarAmount = 8000.0;
		double ballCount = 4.0;
		double maxAmountPerPerson = 3000;
		ArrayList<Double> corrected = new ArrayList<Double>();
		double remaining = 0.0;
		LotteryTicket ticket1 = null;
		// remaining = reDistributeRemainingDollars(
		// Hashtable<Integer, LinkedList<LotteryTicket>> winningTable,
		// double dollarAmount, int ballCount, double maxAmountPerPerson)

	}

	@Test
	public void balancedStrategy2Test1() {
		LotteryService ls = new LotteryService();
		double jackpotGroupPercentage = 60;
		double secondGroupPercentage = 30;
		double thirdGroupPercentage = 10;
		double lotteryPot = 90000;
		double fullRide = 3000;
		Lottery lottery = new Lottery();
		lottery.setFullMatchGuaranteed(false);
		lottery.setLotteryTicketCost(2);
		lottery.setMaxStudentWinnings(fullRide);
		lottery.setMaxTicketsAllowedToPurchase(5);
		lottery.setNumberOfBallsAvailable(15);
		lottery.setNumberOfBallsPicked(5);
		lottery.setStudentWinningPercentage(80);
		lottery.setWinningNumber1(1);
		lottery.setWinningNumber2(2);
		lottery.setWinningNumber3(3);
		lottery.setWinningNumber4(4);
		lottery.setWinningNumber5(5);
		lottery.setWinningNumber6(0);

		LotteryTicket ticket1 = new LotteryTicket(1, 1, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket2 = new LotteryTicket(2, 1, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket3 = new LotteryTicket(3, 1, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket4 = new LotteryTicket(4, 6, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket5 = new LotteryTicket(5, 6, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket6 = new LotteryTicket(6, 6, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket7 = new LotteryTicket(7, 7, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket8 = new LotteryTicket(8, 7, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket9 = new LotteryTicket(9, 8, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket10 = new LotteryTicket(10, 8, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket11 = new LotteryTicket(11, 9, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket12 = new LotteryTicket(12, 10, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket13 = new LotteryTicket(13, 11, 2, 3, 4, 5, 0,27);
		LotteryTicket ticket14 = new LotteryTicket(14, 5, 6, 7, 8, 9, 0,27);
		LotteryTicket ticket15 = new LotteryTicket(15, 6, 7, 8, 9, 10, 0,27);
		LotteryTicket ticket16 = new LotteryTicket(16, 10, 2, 3, 8, 9, 0,27);
		LotteryTicket ticket17 = new LotteryTicket(17, 1, 2, 3, 7, 8, 0,27);
		LotteryTicket ticket18 = new LotteryTicket(18, 10, 2, 13, 4, 5, 0,27);
		LotteryTicket ticket19 = new LotteryTicket(19, 1, 2, 13, 4, 5, 0,27);
		LotteryTicket ticket20 = new LotteryTicket(20, 1, 12, 3, 14, 15, 0,27);
		LotteryTicket ticket21 = new LotteryTicket(21, 11, 12, 13, 14, 15, 0,27);
		LotteryTicket ticket22 = new LotteryTicket(22, 11, 12, 13, 14, 15, 0,27);
		LotteryTicket ticket23 = new LotteryTicket(23, 11, 12, 3, 14, 5, 0,27);
		LotteryTicket ticket24 = new LotteryTicket(24, 1, 12, 3, 14, 15, 0,27);
		LotteryTicket ticket25 = new LotteryTicket(25, 1, 12, 13, 14, 5, 0,27);
		LotteryTicket ticket26 = new LotteryTicket(26, 1, 2, 3, 7, 5, 0,27);
		LotteryTicket ticket27 = new LotteryTicket(27, 1, 6, 8, 4, 5, 0,27);
		LotteryTicket ticket28 = new LotteryTicket(28, 11, 2, 7, 4, 5, 0,27);
		LotteryTicket ticket29 = new LotteryTicket(29, 1, 2, 3, 8, 15, 0,27);
		LotteryTicket ticket30 = new LotteryTicket(30, 1, 12, 13, 8, 15, 0,27);
		LotteryTicket ticket31 = new LotteryTicket(31, 11, 2, 13, 14, 5, 0,27);
		LotteryTicket ticket32 = new LotteryTicket(32, 1, 2, 4, 6, 7, 0,27);
		LotteryTicket ticket33 = new LotteryTicket(33, 11, 2, 13, 4, 15, 0,27);
		LotteryTicket ticket34 = new LotteryTicket(34, 1, 2, 6, 9, 15, 0,27);
		LotteryTicket ticket35 = new LotteryTicket(35, 6, 7, 13, 4, 15, 0,27);

		List<LotteryTicket> tickets = new LinkedList<LotteryTicket>();
		tickets.add(ticket1);
		tickets.add(ticket2);
		tickets.add(ticket3);
		tickets.add(ticket4);
		tickets.add(ticket5);
		tickets.add(ticket6);
		tickets.add(ticket7);
		tickets.add(ticket8);
		tickets.add(ticket9);
		tickets.add(ticket10);
		tickets.add(ticket11);
		tickets.add(ticket12);
		tickets.add(ticket13);
		tickets.add(ticket14);
		tickets.add(ticket15);
		tickets.add(ticket16);
		tickets.add(ticket17);
		tickets.add(ticket18);
		tickets.add(ticket19);
		tickets.add(ticket20);
		tickets.add(ticket21);
		tickets.add(ticket22);
		tickets.add(ticket23);
		tickets.add(ticket24);
		tickets.add(ticket25);
		tickets.add(ticket26);
		tickets.add(ticket27);
		tickets.add(ticket28);
		tickets.add(ticket29);
		tickets.add(ticket30);
		tickets.add(ticket31);
		tickets.add(ticket32);
		tickets.add(ticket33);
		tickets.add(ticket34);
		tickets.add(ticket35);
		Hashtable<Integer, LinkedList<LotteryTicket>> table = ls
				.balancedStrategy2(lottery, tickets, lotteryPot, fullRide,
						jackpotGroupPercentage, secondGroupPercentage,
						thirdGroupPercentage, true);
		Set<Integer> set = table.keySet();

		for (int i = 1; i < set.size(); i++) {

			System.out.println("size: " + table.get(i).size()
					+ "  payout per ticket for this group: "
					+ table.get(i).get(0).getPayout());

		}
		System.out.println(table.toString());
	}
}
