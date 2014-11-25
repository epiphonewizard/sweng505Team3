package com.studentLotto.lottery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
		lottery.setMaxTicketsAllowedToPurchase(editLotteryForm.getMaxTicketsAllowedToPurchase());
		lottery.setNumberOfBallsAvailable(editLotteryForm.getNumberOfBallsAvailable());
		lottery.setNumberOfBallsPicked(editLotteryForm.getNumberOfBallsPicked());
		lottery.setPurchaseEndDate(editLotteryForm.getPurchaseEndDate());
		lottery.setPurchaseStartDate(editLotteryForm.getPurchaseStartDate());
		lottery.setStudentWinningPercentage(editLotteryForm.getStudentWinningPercentage());
	}
	
	@Transactional
	public void deleteLottery(int id) {
		Lottery lottery = lotteryRepository.findOne(id);
		lotteryRepository.remove(lottery);
	}
	
	public void drawWinningNumbers(Lottery lottery, List<LotteryTicket> lotteryTickets) {
		
		Set<Integer> winningNumberSet = pickRandom(lottery.getNumberOfBallsPicked(), lottery.getNumberOfBallsAvailable());
		
		if (lottery.getFullMatchGuaranteed()) {	
			if (atLeastOneFullMatch(lotteryTickets, winningNumberSet)) {
				lottery.setWinningNumbers(winningNumberSet);
				lotteryRepository.update(lottery);
			} else {
				drawWinningNumbers(lottery, lotteryTickets);
			}
			
		} else {
			lottery.setWinningNumbers(winningNumberSet);
			lotteryRepository.update(lottery);
		}
	}
	
	private Set<Integer> pickRandom(int numberOfBalls, int numberOfElements) {
	    Random random = new Random(); // if this method is used often, perhaps define random at class level
	    Set<Integer> picked = new HashSet<>();
	    while(picked.size() < numberOfBalls) {
	        picked.add(random.nextInt(numberOfElements) + 1);
	    }
	    return picked;
	}
	
	private boolean atLeastOneFullMatch(List<LotteryTicket> lotteryTickets, Set<Integer> winningNumberSet) {
		for (LotteryTicket ticket : lotteryTickets) {
			Set<Integer> ticketNumberSet = ticket.getTicketNumbers();
			int winningNumberCount = 0;
			for (int winningNumber : winningNumberSet){
				if(ticketNumberSet.contains(winningNumber)){
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
		List<LotteryTicket> fullMatchSet = identifyWinningTicketsBySpecifiedMatchAmount(lottery, lottery.getNumberOfBallsPicked());
		List<LotteryTicket> oneOffFullMatchSet = identifyWinningTicketsBySpecifiedMatchAmount(lottery, lottery.getNumberOfBallsPicked()-1);
		List<LotteryTicket> twoOffFullMatchSet = identifyWinningTicketsBySpecifiedMatchAmount(lottery, lottery.getNumberOfBallsPicked()-2);
		
		Double lotteryWinnings = lotteryRepository.calculateLotteryWinnings(lottery);
		Double maxWinningsPerStudent = 25.00;
		
		Double studentWinnings = lotteryWinnings * (lottery.getStudentWinningPercentage() / 100);
		
		//full match payout
		if (fullMatchSet.size() > 0) {
			
			Double winningsPerFullMatch = studentWinnings / fullMatchSet.size();
		
			if (winningsPerFullMatch > maxWinningsPerStudent) {
				for (LotteryTicket ticket : fullMatchSet) {
					ticket.setPayout(maxWinningsPerStudent);
					ticket.setWinFlag(1);
					purchaseTicketRepo.update(ticket);
					studentWinnings = studentWinnings - maxWinningsPerStudent;
				}
			} else {
				for (LotteryTicket ticket : fullMatchSet) {
					ticket.setPayout(winningsPerFullMatch);
					ticket.setWinFlag(1);
					purchaseTicketRepo.update(ticket);
					studentWinnings = studentWinnings - winningsPerFullMatch;
				}
			}	
		} 
		// one off full match payout		
		if (oneOffFullMatchSet.size() > 0 && studentWinnings > 0) {
			
			Double winningsPerOneOffFullMatch = studentWinnings / oneOffFullMatchSet.size();
			
			if (winningsPerOneOffFullMatch > maxWinningsPerStudent) {
				for (LotteryTicket ticket : oneOffFullMatchSet) {
					ticket.setPayout(maxWinningsPerStudent);
					ticket.setWinFlag(1);
					purchaseTicketRepo.update(ticket);
					studentWinnings = studentWinnings - maxWinningsPerStudent;
				}
			} else {
				for (LotteryTicket ticket : oneOffFullMatchSet) {
					ticket.setPayout(winningsPerOneOffFullMatch);
					ticket.setWinFlag(1);
					purchaseTicketRepo.update(ticket);
					studentWinnings = studentWinnings - winningsPerOneOffFullMatch;
				}
			}
		}
		
		//two off full match payout
		if (twoOffFullMatchSet.size() > 0 && studentWinnings > 0) {
			
			Double winningsPerTwoOffFullMatch = studentWinnings / twoOffFullMatchSet.size();
			
			if (winningsPerTwoOffFullMatch > maxWinningsPerStudent) {
				for (LotteryTicket ticket : twoOffFullMatchSet) {
					ticket.setPayout(maxWinningsPerStudent);
					ticket.setWinFlag(1);
					purchaseTicketRepo.update(ticket);
					studentWinnings = studentWinnings - maxWinningsPerStudent;
				}
			} else {
				for (LotteryTicket ticket : twoOffFullMatchSet) {
					ticket.setPayout(winningsPerTwoOffFullMatch);
					ticket.setWinFlag(1);
					purchaseTicketRepo.update(ticket);
					studentWinnings = studentWinnings - winningsPerTwoOffFullMatch;
				}
			}	
		}
	}
	
	private List<LotteryTicket> identifyWinningTicketsBySpecifiedMatchAmount(Lottery lottery, int ballsToMatch) {
		Set<Integer> winningNumberSet = lottery.getWinningNumbers();
		List<LotteryTicket> lotteryTickets = purchaseTicketRepo.findTicketsForLottery(lottery.getId());
		List<LotteryTicket> winningTickets = new ArrayList<LotteryTicket>();
		
		for (LotteryTicket ticket : lotteryTickets) {
			Set<Integer> ticketNumberSet = ticket.getTicketNumbers();
			int winningNumberCount = 0;
			for (int winningNumber : winningNumberSet){
				if(ticketNumberSet.contains(winningNumber)){
					winningNumberCount++;
				}
			}
			if (winningNumberCount == ballsToMatch) {
				ticket.setWinDescription("Matched " + ballsToMatch + " of " + lottery.getNumberOfBallsPicked() + " numbers.");
				winningTickets.add(ticket);
			}
		}
		return winningTickets;
	}
	
	public void notifyWinners(int sid, double payout){
		new MailSenderImpl().sendMail("sweng505team3@gmail.com", studentRepository.findWinnerEmail(sid).getUEmailAddress(), 
				"Congratulations - You have Won", 
				new MessageCreator().notifyWinner(String.valueOf(payout)));
	}
		

}
