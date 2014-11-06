package com.studentLotto.lottery.transaction;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountEditForm;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.lottery.donation.DonateForm;
import com.studentLotto.lottery.donation.DonateRepository;
import com.studentLotto.lottery.donation.Donation;
import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.university.University;

@Controller
public class TransactionController {
	@Autowired
	private DonateRepository donationRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	

	@Autowired
	private LotteryRepository lotteryRepository;	
	
	@Autowired
	private CreditCardTransactionRepository ccTransactionRepository;
	
	@RequestMapping(value="bill/pay", method=RequestMethod.GET)
	public String payBill(Principal principal, Model model){
		Assert.notNull(principal);
    	Account account = accountRepository.findByEmail(principal.getName());
    	
		List<Donation> donations = donationRepository.findUnpaidForAccount(account.getId());
		
		model.addAttribute("donations", donations);
		PayBillForm payBillForm = new PayBillForm();
		payBillForm.setDonationIDs(getDonationIDs(donations));
		payBillForm.setAmount( getTotal(donations));
		model.addAttribute("payBillForm", payBillForm);
		
		return "payments/paybill";		
	}
	
	private String getDonationIDs(List<Donation> donations) {
		StringBuilder sb = new StringBuilder();
		for(Donation donation : donations){
			if(sb.length() > 0) 
				sb.append(",");
			sb.append(donation.getId().toString());
		}
		return sb.toString();
	}

	@RequestMapping(value="bill/pay", method=RequestMethod.POST)
	public String payBill(Principal principal, @Valid @ModelAttribute PayBillForm payBillForm,
	Errors errors, RedirectAttributes ra, Model model){
		Assert.notNull(principal);
		String[] idList = payBillForm.getDonationIDs().split(",");
		List<Donation> donations = new ArrayList<Donation>();
		for(String id: idList){
			donations.add(donationRepository.findById(Long.valueOf(id)));
		}		
		if (errors.hasErrors()) {
			model.addAttribute("donations", donations);
			model.addAttribute("donationIDs", getDonationIDs(donations));
			model.addAttribute("payBillForm", payBillForm);
			model.addAttribute("totalBill", getTotal(donations));
			return "payments/paybill";
		}
    	Account account = accountRepository.findByEmail(principal.getName());    	
		CreditCardTransaction transaction = ccTransactionRepository.saveAndFlush(payBillForm.createCCTransaction(account));
		
		for (Donation donation: donations) {
			donation.setPaymentComplete(true);	
			donation.setCreditCardTransaction(transaction);
			Donation updatedDonation = donationRepository.update(donation);
			Lottery lottery = updatedDonation.getLottery();
			lottery.addToMaxWinnings(updatedDonation.getAmount());
			lotteryRepository.update(lottery);
		}	
		MessageHelper.addSuccessAttribute(ra, "payment.successful");    			
		
		return "redirect:/";		
	}

	private Double getTotal(List<Donation> donations) {
		Double total = 0.0;
		for(Donation donation : donations){
			total = total + donation.getAmount();
		}
		return total;
	}
}
