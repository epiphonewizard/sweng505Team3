package com.studentLotto.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.account.StudentRepository;
import com.studentLotto.student.LotteryTicket;
import com.studentLotto.student.PurchaseTicketRepo;
import com.studentLotto.support.mail.MailSenderImpl;
import com.studentLotto.support.mail.MessageCreator;

@Service
public class LotteryService {

	@Autowired
	private LotteryRepository lotteryRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private PurchaseTicketRepo purchaseTicketRepo;

	@Transactional
	public void editLottery(EditLotteryForm editLotteryForm) {
		Lottery lottery = lotteryRepository.findOne(editLotteryForm.getId());
		lottery.setDrawingDate(editLotteryForm.getDrawingDate());
		lottery.setFullMatchGuaranteed(editLotteryForm.getFullMatchGuaranteed());
		lottery.setLotteryTicketCost(editLotteryForm.getLotteryTicketCost());
		lottery.setMaxTicketsAllowedToPurchase(editLotteryForm
				.getMaxTicketsAllowedToPurchase());
		lottery.setNumberOfBallsAvailable(editLotteryForm
				.getNumberOfBallsAvailable());
		lottery.setNumberOfBallsPicked(editLotteryForm.getNumberOfBallsPicked());
		lottery.setPurchaseEndDate(editLotteryForm.getPurchaseEndDate());
		lottery.setPurchaseStartDate(editLotteryForm.getPurchaseStartDate());
		lottery.setStudentWinningPercentage(editLotteryForm
				.getStudentWinningPercentage());
		lottery.setMaxStudentWinnings(editLotteryForm.getMaxStudentWinnings());
		lottery.setStrategy(editLotteryForm.getStrategy());
		lotteryRepository.update(lottery);
	}

	@Transactional
	public void deleteLottery(int id) {
		Lottery lottery = lotteryRepository.findOne(id);
		lotteryRepository.remove(lottery);
	}

	public void drawWinningNumbers(Lottery lottery,
			List<LotteryTicket> lotteryTickets) {

		Set<Integer> winningNumberSet = pickRandom(
				lottery.getNumberOfBallsPicked(),
				lottery.getNumberOfBallsAvailable());

		if (lottery.getFullMatchGuaranteed()) {
			while (!atLeastOneFullMatch(lotteryTickets, winningNumberSet)) {
				winningNumberSet = pickRandom(
						lottery.getNumberOfBallsPicked(),
						lottery.getNumberOfBallsAvailable());
			} 
			lottery.setWinningNumbers(winningNumberSet);
			lotteryRepository.update(lottery);

		} else {
			lottery.setWinningNumbers(winningNumberSet);
			lotteryRepository.update(lottery);
		}
	}

	private Set<Integer> pickRandom(int numberOfBalls, int numberOfElements) {
		Random random = new Random(); // if this method is used often, perhaps
										// define random at class level
		Set<Integer> picked = new HashSet<>();
		while (picked.size() < numberOfBalls) {
			picked.add(random.nextInt(numberOfElements) + 1);
		}
		return picked;
	}

	private boolean atLeastOneFullMatch(List<LotteryTicket> lotteryTickets,
			Set<Integer> winningNumberSet) {
		for (LotteryTicket ticket : lotteryTickets) {
			Set<Integer> ticketNumberSet = ticket.getTicketNumbers();
			int winningNumberCount = 0;
			for (int winningNumber : winningNumberSet) {
				if (ticketNumberSet.contains(winningNumber)) {
					winningNumberCount++;
				}
			}
			if (winningNumberCount == winningNumberSet.size()) {
				return true;
			}
		}
		return false;
	}

	public void payoutLottery(Lottery lottery) {
		if (lottery.getStrategy() == 2) {

			balancedStrategy2(lottery,
					purchaseTicketRepo.findPaidTicketsForLottery(lottery
							.getId()),
					lotteryRepository.calculateLotteryWinnings(lottery),
					lottery.getMaxStudentWinnings(), 60.0, 10.0, 30.0, false);

		} else if (lottery.getStrategy() == 1) {
			List<LotteryTicket> fullMatchSet = identifyWinningTicketsBySpecifiedMatchAmount(
					lottery, lottery.getNumberOfBallsPicked());
			List<LotteryTicket> oneOffFullMatchSet = identifyWinningTicketsBySpecifiedMatchAmount(
					lottery, lottery.getNumberOfBallsPicked() - 1);
			List<LotteryTicket> twoOffFullMatchSet = identifyWinningTicketsBySpecifiedMatchAmount(
					lottery, lottery.getNumberOfBallsPicked() - 2);

			Double lotteryWinnings = lotteryRepository
					.calculateLotteryWinnings(lottery);
			Double maxWinningsPerStudent = lottery.getMaxStudentWinnings();
			Double studentWinnings = lotteryWinnings
					* (lottery.getStudentWinningPercentage() / 100);

			// full match payout
			if (fullMatchSet.size() > 0) {

				Double winningsPerFullMatch = studentWinnings
						/ fullMatchSet.size();

				if (winningsPerFullMatch > maxWinningsPerStudent) {
					for (LotteryTicket ticket : fullMatchSet) {
						ticket.setPayout(maxWinningsPerStudent);
						ticket.setWinFlag(1);
						purchaseTicketRepo.update(ticket);
						//notifyWinners(ticket.getStudent().getId(), ticket.getPayout());
						studentWinnings = studentWinnings
								- maxWinningsPerStudent;
					}
				} else {
					for (LotteryTicket ticket : fullMatchSet) {
						ticket.setPayout(winningsPerFullMatch);
						ticket.setWinFlag(1);
						purchaseTicketRepo.update(ticket);
						studentWinnings = studentWinnings
								- winningsPerFullMatch;
					}
				}
			}
			// one off full match payout
			if (oneOffFullMatchSet.size() > 0 && studentWinnings > 0) {

				Double winningsPerOneOffFullMatch = studentWinnings
						/ oneOffFullMatchSet.size();

				if (winningsPerOneOffFullMatch > maxWinningsPerStudent) {
					for (LotteryTicket ticket : oneOffFullMatchSet) {
						ticket.setPayout(maxWinningsPerStudent);
						ticket.setWinFlag(1);
						purchaseTicketRepo.update(ticket);
						studentWinnings = studentWinnings
								- maxWinningsPerStudent;
					}
				} else {
					for (LotteryTicket ticket : oneOffFullMatchSet) {
						ticket.setPayout(winningsPerOneOffFullMatch);
						ticket.setWinFlag(1);
						purchaseTicketRepo.update(ticket);
						studentWinnings = studentWinnings
								- winningsPerOneOffFullMatch;
					}
				}
			}

			// two off full match payout
			if (twoOffFullMatchSet.size() > 0 && studentWinnings > 0) {

				Double winningsPerTwoOffFullMatch = studentWinnings
						/ twoOffFullMatchSet.size();

				if (winningsPerTwoOffFullMatch > maxWinningsPerStudent) {
					for (LotteryTicket ticket : twoOffFullMatchSet) {
						ticket.setPayout(maxWinningsPerStudent);
						ticket.setWinFlag(1);
						purchaseTicketRepo.update(ticket);
						studentWinnings = studentWinnings
								- maxWinningsPerStudent;
					}
				} else {
					for (LotteryTicket ticket : twoOffFullMatchSet) {
						ticket.setPayout(winningsPerTwoOffFullMatch);
						ticket.setWinFlag(1);
						purchaseTicketRepo.update(ticket);
						studentWinnings = studentWinnings
								- winningsPerTwoOffFullMatch;
					}
				}
			}
		}
	}

	private List<LotteryTicket> identifyWinningTicketsBySpecifiedMatchAmount(
			Lottery lottery, int ballsToMatch) {
		Set<Integer> winningNumberSet = lottery.getWinningNumbers();
		List<LotteryTicket> lotteryTickets = purchaseTicketRepo
				.findPaidTicketsForLottery(lottery.getId());
		List<LotteryTicket> winningTickets = new ArrayList<LotteryTicket>();

		for (LotteryTicket ticket : lotteryTickets) {
			Set<Integer> ticketNumberSet = ticket.getTicketNumbers();
			int winningNumberCount = 0;
			for (int winningNumber : winningNumberSet) {
				if (ticketNumberSet.contains(winningNumber)) {
					winningNumberCount++;
				}
			}
			if (winningNumberCount == ballsToMatch) {
				ticket.setWinDescription("Matched " + ballsToMatch + " of "
						+ lottery.getNumberOfBallsPicked() + " numbers.");
				winningTickets.add(ticket);
			}
		}
		return winningTickets;
	}

	public void notifyWinners(long sid, double payout) {
		new MailSenderImpl().sendMail("sweng505team3@gmail.com",
				studentRepository.findWinnerEmail(sid).getUEmailAddress(),
				"Congratulations - You have Won",
				new MessageCreator().notifyWinner(String.valueOf(payout)));
	}

	// Where are we storing the full ride amount?
	// Where are we storing the lottery pot? or is it calculated on the fly
	public Hashtable<Integer, LinkedList<LotteryTicket>> balancedStrategy2(
			Lottery lottery, List<LotteryTicket> tickets, double lotteryPot,
			double fullRide, double jackpotGroupPercentage,
			double secondGroupPercentage, double thirdGroupPercentage,
			boolean isTest) {
		// get the group weight
		if (((jackpotGroupPercentage + secondGroupPercentage + thirdGroupPercentage) != 100.0)) {
			System.out.println("Error: Percentages do not amount to 100% ");
		}
		double unclaminedMoney = 0.0;
		// get if full match was required and make sure we have at least 1 full
		// match in the supplied lottery ticket
		// this check is done as an integrity check
		boolean isFullMatchGuaranteed = lottery.getFullMatchGuaranteed();

		// get the number of configured balls for the lottery
		int ballCount = lottery.getNumberOfBallsPicked();

		// Each group represent the total matching balls. The max we can match
		// is equivalent to ball count
		ArrayList<Integer> matchingTicketPerGroup = new ArrayList<Integer>(
				ballCount);
		ArrayList<Integer> matchingTicketPerGroupAdjusted = new ArrayList<Integer>(
				ballCount);

		// Define a hashtable with an int key and lottery ticket list as the
		// value for that key
		// In essence the jackpot group will be located at key 1 with the
		// corresponding winning tickets
		// the second group is located at key 2 with the corresponding winning
		// tickets
		// finally the third group is located at key 3 with the corresponding
		// winning tickets
		Hashtable<Integer, LinkedList<LotteryTicket>> winningTable = new Hashtable<Integer, LinkedList<LotteryTicket>>();

		// convert the lottery ticket winning numbers to a sorted set so it is
		// easy to compare tickets
		SortedSet<Integer> jackpotTicketNumbers = convertTicketToSortedSet(
				lottery.getWinningNumber1(), lottery.getWinningNumber2(),
				lottery.getWinningNumber3(), lottery.getWinningNumber4(),
				lottery.getWinningNumber5(), lottery.getWinningNumber6(),
				ballCount);

		Iterator<LotteryTicket> ticketIterator = tickets.iterator();
		SortedSet<Integer> currTicketSortedSet = null;
		LotteryTicket currTicket = null;
		while (ticketIterator.hasNext()) {
			currTicket = ticketIterator.next();
			currTicketSortedSet = convertTicketToSortedSet(
					currTicket.getFirstNumber(), currTicket.getSecondNumber(),
					currTicket.getThirdNumber(), currTicket.getFourthNumber(),
					currTicket.getFifthNumber(), currTicket.getSixthNumber(),
					ballCount);

			int matchingNumberCount = getMatchingNumberCount(
					currTicketSortedSet, jackpotTicketNumbers);
			int winningCategory = getWinnercategory(matchingNumberCount,
					ballCount);

			// Something went wrong !
			if (matchingNumberCount == -1 || winningCategory == -1) {
				System.out
						.println("ERROR: MATCHING NUMBER COUNT OR WINNING CATEGORY: "
								+ matchingNumberCount
								+ "  :  "
								+ winningCategory);
				continue;
			}

			// Otherwise, we seem to have valid data so we can tie the ticket to
			// its category
			// if it is our first time finding a number for a given category,
			// then add a brand new category to the table
			if (!winningTable.containsKey(winningCategory)) {
				LinkedList<LotteryTicket> categoryList = new LinkedList<LotteryTicket>();
				categoryList.add(currTicket);
				winningTable.put(winningCategory, categoryList);

			}// otherwise, fetch the category from the table and update it
			else {
				winningTable.get(winningCategory).add(currTicket);
			}
			// At this stage, we have all the tickets sorted by category in the
			// category table.
			// Note categories 1,2 & 3 will be mainly winning money.
			// In the unlikely event that after paying out full ride for all
			// these 3 categories!! we will actually start considering
			// paying out the 4th category then the fifth category then the
			// sixth category
		}
		// This is an integrity check
		if (isFullMatchGuaranteed == true) {
			// lets now check if there was any ticket with full match!
			if (!winningTable.contains(1)) {
				System.out
						.println("ERROR!!: full match guaranteed set to true, but there is no full match!");
			}
		}

		// get sorted key set
		Set<Integer> keys = winningTable.keySet();
		LinkedList<Integer> keyList = new LinkedList<Integer>(keys);
		Collections.sort(keyList);
		Iterator<Integer> keyIterator = keyList.iterator();
		// int index = 0;
		// for each key set, populate the matching ticket per group :
		// "how many tickets matched per ball group"
		while (keyIterator.hasNext()) {
			LinkedList<LotteryTicket> keyTicket = winningTable.get(keyIterator
					.next());
			matchingTicketPerGroup.add(keyTicket.size());
			// index += 1;
		}

		// We adjust the matching tickets to preserve fairness. For example the
		// ticket that matches 6 balls is considered as a winner
		// that matched 6, 5 and 4 balls
		matchingTicketPerGroupAdjusted = calculateMatchingTicketAdjusted(matchingTicketPerGroup);

		// Now we check if we have any matches at all. If we dont. we distribute
		// the wealth among the remaining categories
		if (matchingTicketPerGroupAdjusted.get(0) == 0
				&& matchingTicketPerGroupAdjusted.get(1) == 0
				&& matchingTicketPerGroupAdjusted.get(2) == 0) {
			unclaminedMoney = reDistributeRemainingDollars(winningTable,
					lotteryPot, ballCount, fullRide, lottery, false);
			// save unclaimed money to the database
			return winningTable;
		} else {
			// adjust the percentages because we are trying to pay off all the
			// allocated money to the top 3 groups
			// for example , if there was 0 jackpot matches , allocate the
			// jackpot
			// percentage to the group below it
			ArrayList<Double> adjustedPercentages = getAdjustedPercentages(
					matchingTicketPerGroup.get(0),
					matchingTicketPerGroup.get(1),
					matchingTicketPerGroup.get(2), jackpotGroupPercentage,
					secondGroupPercentage, thirdGroupPercentage);

			ArrayList<Double> moneyAllocatedPerGroup = getMoneyAllocatedPerGroup(
					adjustedPercentages, lotteryPot);

			ArrayList<Double> moneyAllocatedPerPersonPerGroup = getMoneyAllocatedPerPersonPerGroup(
					moneyAllocatedPerGroup, matchingTicketPerGroupAdjusted);

			ArrayList<Double> rawCalculatedMoneyPerPerson = getMoneyRawCalculatedPerPerson(
					moneyAllocatedPerPersonPerGroup, adjustedPercentages);

			ArrayList<ArrayList<Double>> retObj = getMoneyRawCalculatedPerPersonCorrected(
					rawCalculatedMoneyPerPerson, fullRide,
					matchingTicketPerGroup);

			ArrayList<Double> remainder = (ArrayList<Double>) retObj.get(0);
			ArrayList<Double> adjusted = (ArrayList<Double>) retObj.get(1);
			ArrayList<Double> corrected = (ArrayList<Double>) retObj.get(2);

			double totalPayoutForTopThreeGroups = getTotalPayoutForTopThreeGroupsAndIssuePayout(
					corrected, winningTable, isTest);
			if (totalPayoutForTopThreeGroups > lotteryPot) {
				System.out
						.println("SOmething went wrong!: we paid more than what we have in the pot!");
				return null;
			}
			double remainderAfterPayout = lotteryPot
					- totalPayoutForTopThreeGroups;
			if (remainderAfterPayout <= 0) {
				// we are done at this point . THere was no remainder money to
				// provide to other categories
				return winningTable;
			} else {

				// in this case there was a remainder after top 3 categories pay
				// out! we
				// need to re distribute the wealth again among the remaining
				// categories!!!
				unclaminedMoney = reDistributeRemainingDollars(winningTable,
						remainderAfterPayout, ballCount,
						getMinPersonPayout(corrected, matchingTicketPerGroup),
						lottery, isTest);

				// save unclaimed money to the database
			}
		}

		// in this case there was a remainder after top 3 categories pay out! we
		// need to re distribute the wealth again!!!

		return winningTable;
	}

	/**
	 * Takes six lottery ticket numbers and returns a sorted set limited to the
	 * size of the ball count This is used to easily compare tickets
	 * 
	 * @param firstNumber
	 * @param secondNumber
	 * @param thirdNumber
	 * @param fourthNumber
	 * @param fifthNumber
	 * @param sixthNumber
	 * @param ballCount
	 * @return
	 */
	public static SortedSet<Integer> convertTicketToSortedSet(int firstNumber,
			int secondNumber, int thirdNumber, int fourthNumber,
			int fifthNumber, int sixthNumber, int ballCount) {
		// define the sorted set
		SortedSet<Integer> ticketNumbers = new TreeSet<Integer>();
		// always add the first 4 numbers
		ticketNumbers.add(firstNumber);
		ticketNumbers.add(secondNumber);
		ticketNumbers.add(thirdNumber);
		ticketNumbers.add(fourthNumber);
		// if the lottery configuration supports 5+ numbers then add the fifth
		// number to the sorted set
		if (ballCount >= 5) {
			ticketNumbers.add(fifthNumber);
		}// if the lottery configuration supports 6 numbers then add the sixth
			// number to the sorted set
		if (ballCount >= 6) {
			ticketNumbers.add(sixthNumber);
		}

		return ticketNumbers;
	}

	public static int getMatchingNumberCount(SortedSet<Integer> studentTicket,
			SortedSet<Integer> jackpotTicketNumbers) {

		if (studentTicket.size() != jackpotTicketNumbers.size()) {
			// there was an issue these two size should match
			System.out.println("ERROR: Sets  size don't match");
			return -1;
		}
		if (studentTicket.size() > 6 || studentTicket.size() == 0
				|| jackpotTicketNumbers.size() > 6
				|| jackpotTicketNumbers.size() == 0) {
			System.out
					.println("ERROR: Input size doest not make sense per our restrictions");
			return -1;
		}
		SortedSet<Integer> intersection = new TreeSet<Integer>(studentTicket);
		intersection.retainAll(jackpotTicketNumbers);

		return intersection.size();
	}

	public int getWinnercategory(int matchingNumberCount, int ballCount) {
		if (matchingNumberCount < 0 || ballCount < 0) {
			System.out
					.println("Error: cant have negative values for ballcount and matching num count");
			return -1;
		}
		if (matchingNumberCount > ballCount) {
			System.out
					.println("Error: cant matchign balls more then what we have");
			return -1;
		}
		if (ballCount < 4 || ballCount > 6 || matchingNumberCount > 6) {
			System.out
					.println("Error, values dont make sense per our restrictions!");
			return -1;
		}

		// in this case , lets just append these 0 matches to the end of the set
		if (matchingNumberCount == 0) {
			return ballCount + 1;
		}

		// if all numbers match, then it is category 1 (or the jackpot category)
		if (matchingNumberCount == ballCount) {
			return 1;
			// if all the numbers match but 1 number, it is category 2 (still a
			// winner)
		}
		if (matchingNumberCount == ballCount - 1) {
			return 2;
			// if all the numbers match but 2 numbers, it is category 3 (still a
			// winner)
		}
		if (matchingNumberCount == ballCount - 2) {
			return 3;
		}
		if (matchingNumberCount == ballCount - 3) {
			return 4;
		}

		if (ballCount == 5) {
			if (matchingNumberCount == ballCount - 4) {
				return 5;
			}
		} else if (ballCount == 6) {
			if (matchingNumberCount == ballCount - 5) {
				return 6;
			}
		}

		System.out.println("Something went bad!");
		return -1;
	}

	/**
	 * Adjust the matching ticket such as the winner of a jackpot is also a
	 * winner of jackpot-1 ball and jackpot -2 ball in order to preserve
	 * fairness such that students with more matching ball win more money
	 * 
	 * @param in
	 * @return
	 */
	public ArrayList<Integer> calculateMatchingTicketAdjusted(
			ArrayList<Integer> in) {
		ArrayList<Integer> matchingTicketPerGroupAdjusted = new ArrayList<Integer>(
				in.size());

		int index = 0;

		int matchedTicketsNumAdjusted = 0;
		for (int i = 0; i < in.size(); i++) {
			// reset on going accumulator
			matchedTicketsNumAdjusted = 0;
			// compute the adjusted num total
			for (int j = 0; j < index + 1; j++) {
				matchedTicketsNumAdjusted += in.get(j);
			}

			matchingTicketPerGroupAdjusted.add(matchedTicketsNumAdjusted);
			index += 1;
		}

		return matchingTicketPerGroupAdjusted;
	}

	/**
	 * Returns the adjusted percentages. For example if the jackpot group has 0
	 * winning tickets , then set the percentage for that group to 0.0 and add
	 * that missing percentage to the group below. This is done because we are
	 * trying to payout everything (as opposed to roll the remainder to the next
	 * lottery
	 * 
	 * @param firstMatch
	 * @param secondMatch
	 * @param thirdMatch
	 * @param firstPercentage
	 * @param secondPercentage
	 * @param thirdPercentage
	 * @return
	 */
	public ArrayList<Double> getAdjustedPercentages(int firstMatch,
			int secondMatch, int thirdMatch, double firstPercentage,
			double secondPercentage, double thirdPercentage) {
		ArrayList<Double> adjustedPercentages = new ArrayList<Double>(3);
		if ((firstPercentage + secondPercentage + thirdPercentage) > 100.0) {
			System.out.println("PERCENTAGE ERROR! greater than 100%");
		}

		if (firstMatch == 0 && secondMatch == 0 && thirdMatch == 0) {
			firstPercentage = secondPercentage = thirdPercentage = 0;
		} else if (firstMatch == 0 && secondMatch == 0) {
			thirdPercentage = 100;
			secondPercentage = firstPercentage = 0;
		} else if (firstMatch == 0 && thirdMatch == 0) {
			secondPercentage = 100;
			thirdPercentage = firstPercentage = 0;
		} else if (secondMatch == 0 && thirdMatch == 0) {
			firstPercentage = 100;
			thirdPercentage = secondPercentage = 0;
		} else if (firstMatch == 0) {
			secondPercentage += firstPercentage;
			firstPercentage = 0;
			thirdPercentage = 100 - secondPercentage;
		} else if (secondMatch == 0) {
			firstPercentage += secondPercentage;
			secondPercentage = 0;
			thirdPercentage = 100 - firstPercentage;
		} else if (thirdMatch == 0) {
			secondPercentage += thirdPercentage;
			thirdPercentage = 0;
			firstPercentage = 100 - secondPercentage;
		}

		adjustedPercentages.add(0, firstPercentage);
		adjustedPercentages.add(1, secondPercentage);
		adjustedPercentages.add(2, thirdPercentage);

		return adjustedPercentages;
	}

	/**
	 * Calculate the total mount allocated per group taking into //
	 * consideration the adjusted percentages moneyAllocatedPerGroup.add(0,
	 * (adjustedPerc
	 * 
	 * @param adjustedPercentages
	 * @param lotteryPot
	 * @return
	 */
	public ArrayList<Double> getMoneyAllocatedPerGroup(
			ArrayList<Double> adjustedPercentages, double lotteryPot) {
		ArrayList<Double> moneyAllocatedPerGroup = new ArrayList<Double>(3);
		// Calculate the total mount allocated per group taking into
		// consideration the adjusted percentages
		moneyAllocatedPerGroup.add(0, (adjustedPercentages.get(0) / 100.0)
				* lotteryPot);
		moneyAllocatedPerGroup.add(1, (adjustedPercentages.get(1) / 100.0)
				* lotteryPot);
		moneyAllocatedPerGroup.add(2, (adjustedPercentages.get(2) / 100.0)
				* lotteryPot);

		return moneyAllocatedPerGroup;
	}

	public ArrayList<Double> getMoneyAllocatedPerPersonPerGroup(
			ArrayList<Double> moneyAllocatedPerGroup,
			ArrayList<Integer> matchingTicketPerGroupAdjusted) {
		ArrayList<Double> moneyAllocatedPerPersonPerGroup = new ArrayList<Double>(
				3);

		// calculate the expected pay out for the top 3 groups per their
		// percentages
		for (int i = 0; i < 3; i++) {
			// avoid division by 0.0
			if (matchingTicketPerGroupAdjusted.get(i) == 0) {
				moneyAllocatedPerPersonPerGroup.add(0.0);
			} else {
				// calculate the payout if there are any winners
				moneyAllocatedPerPersonPerGroup.add(moneyAllocatedPerGroup
						.get(i) / matchingTicketPerGroupAdjusted.get(i));
			}
		}

		return moneyAllocatedPerPersonPerGroup;
	}

	/**
	 * Calculate the raw money by adding the $ amount of first group, second
	 * group and third to the jackpot winner for example . Note this raw amount
	 * can be greater than full ride! This is why this amount need to be clipped
	 * at full ride
	 * 
	 * @param moneyAllocatedPerPersonPerGroup
	 * @return
	 */
	public ArrayList<Double> getMoneyRawCalculatedPerPerson(
			ArrayList<Double> moneyAllocatedPerPersonPerGroup,
			ArrayList<Double> adjustedPercentage) {
		ArrayList<Double> rawCalculatedMoney = new ArrayList<Double>();
		rawCalculatedMoney.add(0.0);
		rawCalculatedMoney.add(0.0);
		rawCalculatedMoney.add(0.0);
		int group = 0;
		double computeAmount = 0.0;
		// for the first 3 groups
		for (int i = 0; i < 3; i++) {
			computeAmount = 0.0;
			if (adjustedPercentage.get(i) > 0.0) {
				for (int j = group; j < 3; j++) {

					computeAmount += (rawCalculatedMoney.get(i) + moneyAllocatedPerPersonPerGroup
							.get(j));
				}

				rawCalculatedMoney.set(i, computeAmount);
			} else {
				rawCalculatedMoney.set(i, 0.0);
			}
			group += 1;
		}
		return rawCalculatedMoney;

	}

	public ArrayList<ArrayList<Double>> getMoneyRawCalculatedPerPersonCorrected(
			ArrayList<Double> rawCalculatedMoneyPerPerson, double fullRide,
			ArrayList<Integer> matchingTicketPerGroup) {

		ArrayList<ArrayList<Double>> retObj = new ArrayList<ArrayList<Double>>();

		ArrayList<Double> adjustedAmount = new ArrayList<Double>(3);
		// the first group will not have an adjusted amount because they are at
		// the top of the chain
		// they will never receive extra money from remainder
		adjustedAmount.add(rawCalculatedMoneyPerPerson.get(0));
		ArrayList<Double> correctedAmount = new ArrayList<Double>(3);

		ArrayList<Double> remainder = new ArrayList<Double>(3);

		int count = 0;
		while (count < rawCalculatedMoneyPerPerson.size()) {

			// in this case, also the the adjusted percentage should also be set
			// to 0.0
			// we end up paying 0.0 for that group as there is no one in it .
			// Then we move the $ amount to other groups
			if (matchingTicketPerGroup.get(count) == 0) {
				adjustedAmount.add(0.0);
				remainder.add(0.0);
				correctedAmount.add(0.0);
			} else {
				// for the first group the adjusted amount is 0.0 as it received
				// no remained from a Higher group (since no group is higher
				// than jackpot)
				// Note: Adjusted amount at first index for jackpot group was
				// set to raw calculated
				if (count > 0) {
					adjustedAmount
							.add(rawCalculatedMoneyPerPerson.get(count)
									+ ((remainder.get(count - 1) * matchingTicketPerGroup
											.get(count - 1)) / matchingTicketPerGroup
											.get(count)));
				}
				// if the adjusted amount exceeds the full ride. calculate the
				// remainder for that category
				// Also calculated corrected (clip it at full ride)
				if (adjustedAmount.get(count) > fullRide) {
					remainder.add(adjustedAmount.get(count) - fullRide);
					correctedAmount.add(fullRide);

				}// Otherwise, the corrected is same as the adjusted $ amount .
					// The remainder is 0.0
				else {
					remainder.add(0.0);
					correctedAmount.add(adjustedAmount.get(count));
				}

				// adjustedAmount.add(adjustedAmount.get(count) + amount);

			}
			// keep track of the index
			count += 1;
		}
		// add the return values to the array object
		retObj.add(remainder);
		retObj.add(adjustedAmount);
		retObj.add(correctedAmount);
		return retObj;
	}

	public double getTotalPayoutForTopThreeGroupsAndIssuePayout(
			ArrayList<Double> corrected,
			Hashtable<Integer, LinkedList<LotteryTicket>> winningTable,
			boolean isTest) {
		double topThreeCategoriesTotal = 0.0;
		double keepTrackOfTotal = 0.0;
		Set<Integer> keys = winningTable.keySet();
		Iterator<LotteryTicket> it = null;
		LotteryTicket currTicket = null;
		if (keys.contains(new Integer(1))) {
			topThreeCategoriesTotal += (corrected.get(0) * winningTable.get(1)
					.size());
		}
		if (keys.contains(new Integer(2))) {
			topThreeCategoriesTotal += (corrected.get(1) * winningTable.get(2)
					.size());
		}
		if (keys.contains(new Integer(3))) {
			topThreeCategoriesTotal += (corrected.get(2) * winningTable.get(3)
					.size());
		}

		double firstCateogryWinning = corrected.get(0);
		double secondCateogryWinning = corrected.get(1);
		double thirdCateogryWinning = corrected.get(2);
		if (keys.contains(new Integer(1))) {
			LinkedList<LotteryTicket> firstCategoryTickets = winningTable
					.get(1);
			it = firstCategoryTickets.iterator();

			while (it.hasNext()) {
				currTicket = it.next();
				currTicket.setWinFlag(1);
				currTicket.setWinDescription("Jackpot");
				currTicket.setPayout(firstCateogryWinning);
				keepTrackOfTotal += firstCateogryWinning;
				// save to database
				if (isTest == false) {
					notifyWinners(currTicket.getStudent().getId(),
							firstCateogryWinning);
					purchaseTicketRepo.update(currTicket);
				}
			}
		}
		if (keys.contains(new Integer(2))) {
			LinkedList<LotteryTicket> secondCategoryTickets = winningTable
					.get(2);
			it = secondCategoryTickets.iterator();
			while (it.hasNext()) {
				currTicket = it.next();
				currTicket.setWinFlag(1);
				currTicket.setWinDescription("Jackpot-1");
				currTicket.setPayout(secondCateogryWinning);
				keepTrackOfTotal += secondCateogryWinning;
				// save to database
				if (isTest == false) {
					notifyWinners(currTicket.getStudent().getId(),
							secondCateogryWinning);
					purchaseTicketRepo.update(currTicket);
				}
			}
		}
		if (keys.contains(new Integer(3))) {
			LinkedList<LotteryTicket> thirdCategoryTickets = winningTable
					.get(3);
			it = thirdCategoryTickets.iterator();
			while (it.hasNext()) {
				currTicket = it.next();
				currTicket.setWinFlag(1);
				currTicket.setWinDescription("Jackpot-2");
				currTicket.setPayout(thirdCateogryWinning);
				keepTrackOfTotal += thirdCateogryWinning;
				// save to database
				if (isTest == false) {
					notifyWinners(currTicket.getStudent().getId(),
							thirdCateogryWinning);
					purchaseTicketRepo.update(currTicket);
				}
			}
		}
		keepTrackOfTotal = Math.round(keepTrackOfTotal);
		if (Math.abs(keepTrackOfTotal - topThreeCategoriesTotal) > 0.001) {
			System.out.println("INTEGRITY ERROR! :  computed total 2 different"
					+ " ways and ended up with different results: "
					+ keepTrackOfTotal + "  " + topThreeCategoriesTotal);
		}
		return topThreeCategoriesTotal;
	}

	public double reDistributeRemainingDollars(
			Hashtable<Integer, LinkedList<LotteryTicket>> winningTable,
			double dollarAmount, int ballCount, double maxAmountPerPerson,
			Lottery lottery, boolean isTest) {
		double unclaimedMoney = 0.0;
		double paidSoFar = 0.0;
		LinkedList<LotteryTicket> currTicketList = null;
		LotteryTicket currTicket = null;
		Iterator<LotteryTicket> it = null;
		double toPay = 0.0;
		boolean done = false;
		Set<Integer> keys = winningTable.keySet();
		for (int i = 4; i <= ballCount + 1; i++) {
			// if we dont have any winner in this category, then move on
			if (!keys.contains(new Integer(i))) {
				continue;
			}
			currTicketList = winningTable.get(i);
			// deduct the paid so far amount and divide among all tickets within
			// that list.
			// f if we exceed the max amount to pay that means we will have a
			// remainder
			if ((dollarAmount - paidSoFar) / currTicketList.size() > maxAmountPerPerson) {
				toPay = maxAmountPerPerson;

			} else {
				toPay = (dollarAmount - paidSoFar) / currTicketList.size();
				done = true;
			}
			it = currTicketList.iterator();
			while (it.hasNext()) {
				currTicket = it.next();
				currTicket.setWinDescription("Secondaray: Jackpot-3");
				currTicket.setWinFlag(1);
				currTicket.setPayout(toPay);
				paidSoFar += maxAmountPerPerson;
				// save to database
				if (isTest == false) {
					notifyWinners(currTicket.getStudent().getId(), toPay);
					purchaseTicketRepo.update(currTicket);
				}

			}
			if (done) {
				unclaimedMoney = 0.0;
				return unclaimedMoney;
			}
		}
		unclaimedMoney = dollarAmount - paidSoFar;

		lottery.setUnclaimedMoney(unclaimedMoney);
		if (isTest == false) {
			lotteryRepository.update(lottery);
		}

		return unclaimedMoney;
	}

	public double getMinPersonPayout(ArrayList<Double> corrected,
			ArrayList<Integer> matchingTicketPerGroup) {
		double minPersonPayout = Double.MAX_VALUE;
		boolean valUpdated = false;
		// loop through the top 3 categories
		for (int i = 0; i < 3; i++) {
			// first check if there was any match in that category. If there
			// isnt, the payout is 0.0 anyways! that is small but not good data
			// either because it is a "no payout"
			if (matchingTicketPerGroup.get(i) > 0) {
				// if we find a smaller payout, then update my ongoing value
				if (corrected.get(i) < minPersonPayout) {
					minPersonPayout = corrected.get(i);
					// this is a flag that states a min was truly found
					valUpdated = true;
				}
			}
		}

		// if there is not legit min (first 3 categories all 0.0 because there
		// was no match)
		// set the minPersonPayout to 0.0
		if (valUpdated == false) {
			minPersonPayout = 0.0;
		}

		return minPersonPayout;
	}
}
