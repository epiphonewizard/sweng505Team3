package com.studentLotto.lottery;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentLotto.account.AccountRepository;
import com.studentLotto.account.PersonRepository;
import com.studentLotto.account.StudentRepository;
import com.studentLotto.student.LotteryTicket;
import com.studentLotto.student.PurchaseTicketRepo;
import com.studentLotto.support.mail.MailSenderImpl;
import com.studentLotto.support.mail.MessageCreator;

@Service
public class LotteryService {
	
	@Autowired
	private LotteryRepository lotteryRepository;
    private StudentRepository studentRepository;
	
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
		//to be implemented
		int end = lottery.getNumberOfBallsAvailable();
		Random randomGenerator = new Random();

		int firstNumber = randomGenerator.nextInt(end) + 1;
		int secondNumber = randomGenerator.nextInt(end) + 1;
		int thirdNumber = randomGenerator.nextInt(end) + 1;
		int fourthNumber = randomGenerator.nextInt(end) + 1;
		int fifthNumber = randomGenerator.nextInt(end) + 1;
		int sixthNumber = randomGenerator.nextInt(end) + 1;
		
		if (lottery.getFullMatchGuaranteed()) {	
			PurchaseTicketRepo purchaseTicketRepo = new PurchaseTicketRepo();
			List<LotteryTicket> lotteryTickets = purchaseTicketRepo.findTicketsForLottery(lottery.getId());
			if (lottery.getNumberOfBallsPicked() == 4 && atLeastOneFullMatch(lotteryTickets, firstNumber, secondNumber, thirdNumber, fourthNumber)) {
				lottery.setWinningNumbers(firstNumber, secondNumber, thirdNumber, fourthNumber);
				lotteryRepository.save(lottery);
			} else if (lottery.getNumberOfBallsPicked() == 5 && atLeastOneFullMatch(lotteryTickets, firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber)) {
				lottery.setWinningNumbers(firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber);
				lotteryRepository.save(lottery);
			} else if (lottery.getNumberOfBallsPicked() == 6 && atLeastOneFullMatch(lotteryTickets, firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber, sixthNumber)) {
				lottery.setWinningNumbers(firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber, sixthNumber);
				lotteryRepository.save(lottery);
			} else {
				drawWinningNumbers(lottery);
			}
		} else {
			if (lottery.getNumberOfBallsPicked() == 4) {
				lottery.setWinningNumbers(firstNumber, secondNumber, thirdNumber, fourthNumber);
				lotteryRepository.save(lottery);
			} else if (lottery.getNumberOfBallsPicked() == 5) {
				lottery.setWinningNumbers(firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber);
				lotteryRepository.save(lottery);
			} else if (lottery.getNumberOfBallsPicked() == 6) {
				lottery.setWinningNumbers(firstNumber, secondNumber, thirdNumber, fourthNumber, fifthNumber, sixthNumber);
				lotteryRepository.save(lottery);
			}
		}
	}
	
	private boolean atLeastOneFullMatch(List<LotteryTicket> lotteryTickets, int firstNumber, int secondNumber, int thirdNumber, int fourthNumber) {
		for (LotteryTicket ticket : lotteryTickets) {
			if ((ticket.getFirstNumber()==firstNumber || ticket.getFirstNumber()==secondNumber || ticket.getFirstNumber()==thirdNumber || ticket.getFirstNumber()==fourthNumber) 
				&& (ticket.getSecondNumber()==firstNumber || ticket.getSecondNumber()==secondNumber || ticket.getSecondNumber()==thirdNumber || ticket.getSecondNumber()==fourthNumber)
				&& (ticket.getThirdNumber()==firstNumber || ticket.getThirdNumber()==secondNumber || ticket.getThirdNumber()==thirdNumber || ticket.getThirdNumber()==fourthNumber)
				&& (ticket.getFourthNumber()==firstNumber || ticket.getFourthNumber()==secondNumber || ticket.getFourthNumber()==thirdNumber || ticket.getFourthNumber()==fourthNumber)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean atLeastOneFullMatch(List<LotteryTicket> lotteryTickets, int firstNumber, int secondNumber, int thirdNumber, int fourthNumber, int fifthNumber) {
		for (LotteryTicket ticket : lotteryTickets) {
			if ((ticket.getFirstNumber()==firstNumber || ticket.getFirstNumber()==secondNumber || ticket.getFirstNumber()==thirdNumber || ticket.getFirstNumber()==fourthNumber || ticket.getFirstNumber()==fifthNumber) 
				&& (ticket.getSecondNumber()==firstNumber || ticket.getSecondNumber()==secondNumber || ticket.getSecondNumber()==thirdNumber || ticket.getSecondNumber()==fourthNumber || ticket.getSecondNumber()==fifthNumber)
				&& (ticket.getThirdNumber()==firstNumber || ticket.getThirdNumber()==secondNumber || ticket.getThirdNumber()==thirdNumber || ticket.getThirdNumber()==fourthNumber || ticket.getThirdNumber()==fifthNumber)
				&& (ticket.getFourthNumber()==firstNumber || ticket.getFourthNumber()==secondNumber || ticket.getFourthNumber()==thirdNumber || ticket.getFourthNumber()==fourthNumber  || ticket.getFourthNumber()==fifthNumber)
				&& (ticket.getFifthNumber()==firstNumber || ticket.getFifthNumber()==secondNumber || ticket.getFifthNumber()==thirdNumber || ticket.getFifthNumber()==fourthNumber  || ticket.getFifthNumber()==fifthNumber)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean atLeastOneFullMatch(List<LotteryTicket> lotteryTickets, int firstNumber, int secondNumber, int thirdNumber, int fourthNumber, int fifthNumber, int sixthNumber) {
		for (LotteryTicket ticket : lotteryTickets) {
			if ((ticket.getFirstNumber()==firstNumber || ticket.getFirstNumber()==secondNumber || ticket.getFirstNumber()==thirdNumber || ticket.getFirstNumber()==fourthNumber || ticket.getFirstNumber()==fifthNumber || ticket.getFirstNumber()==sixthNumber) 
				&& (ticket.getSecondNumber()==firstNumber || ticket.getSecondNumber()==secondNumber || ticket.getSecondNumber()==thirdNumber || ticket.getSecondNumber()==fourthNumber || ticket.getSecondNumber()==fifthNumber || ticket.getSecondNumber()==sixthNumber)
				&& (ticket.getThirdNumber()==firstNumber || ticket.getThirdNumber()==secondNumber || ticket.getThirdNumber()==thirdNumber || ticket.getThirdNumber()==fourthNumber || ticket.getThirdNumber()==fifthNumber || ticket.getThirdNumber()==sixthNumber)
				&& (ticket.getFourthNumber()==firstNumber || ticket.getFourthNumber()==secondNumber || ticket.getFourthNumber()==thirdNumber || ticket.getFourthNumber()==fourthNumber  || ticket.getFourthNumber()==fifthNumber || ticket.getFourthNumber()==sixthNumber)
				&& (ticket.getFifthNumber()==firstNumber || ticket.getFifthNumber()==secondNumber || ticket.getFifthNumber()==thirdNumber || ticket.getFifthNumber()==fourthNumber  || ticket.getFifthNumber()==fifthNumber  || ticket.getFifthNumber()==sixthNumber)
				&& (ticket.getSixthNumber()==firstNumber || ticket.getSixthNumber()==secondNumber || ticket.getSixthNumber()==thirdNumber || ticket.getSixthNumber()==fourthNumber  || ticket.getSixthNumber()==fifthNumber  || ticket.getSixthNumber()==sixthNumber)) {
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
