package com.studentLotto.lottery;

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
	
	public void drawWinningNumbers(Lottery lottery) {
		
		Set<Integer> winningNumberSet = pickRandom(lottery.getNumberOfBallsPicked(), lottery.getNumberOfBallsAvailable());
		
		if (lottery.getFullMatchGuaranteed()) {	
			int lotteryId = lottery.getId();
			List<LotteryTicket> lotteryTickets = purchaseTicketRepo.findTicketsForLottery(lotteryId);
			
			if (atLeastOneFullMatch(lotteryTickets, winningNumberSet)) {
				lottery.setWinningNumbers(winningNumberSet);
				lotteryRepository.update(lottery);
			} else {
				drawWinningNumbers(lottery);
			}
			
		} else {
			lottery.setWinningNumbers(winningNumberSet);
			lotteryRepository.update(lottery);
		}
	}
	
	public Set<Integer> pickRandom(int numberOfBalls, int numberOfElements) {
	    Random random = new Random(); // if this method is used often, perhaps define random at class level
	    Set<Integer> picked = new HashSet<>();
	    while(picked.size() < numberOfBalls) {
	        picked.add(random.nextInt(numberOfElements + 1));
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
	
	public void determinePayout(Lottery lottery) {
		//to be implemented
	}
	
	public void notifyWinners(int sid, double payout){
		new MailSenderImpl().sendMail("sweng505team3@gmail.com", studentRepository.findWinnerEmail(sid).getUEmailAddress(), 
				"Congratulations - You have Won", 
				new MessageCreator().notifyWinner(String.valueOf(payout)));
	}
		

}
