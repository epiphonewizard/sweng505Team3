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
import org.thymeleaf.util.StringUtils;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountEditForm;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.lottery.donation.DonationForm;
import com.studentLotto.lottery.donation.DonationRepository;
import com.studentLotto.lottery.donation.Donation;
import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.university.University;

@Controller
public class TransactionController {
	@Autowired
	private DonationRepository donationRepository;
	
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
		payBillForm.setDonationIDs(Donation.getDonationIDs(donations));
		payBillForm.setAmount( Donation.getTotal(donations));
		model.addAttribute("payBillForm", payBillForm);
		
		return "payments/paybill";		
	}
	


	@RequestMapping(value="bill/pay", method=RequestMethod.POST)
	public String payBill(Principal principal, @Valid @ModelAttribute PayBillForm payBillForm,
	Errors errors, RedirectAttributes ra, Model model){
		Assert.notNull(principal);
		String[] idList = payBillForm.getDonationIDs().split(",");
		List<Donation> donations = new ArrayList<Donation>();
		for(String id: idList){
			if(!StringUtils.isEmptyOrWhitespace(id)){
				donations.add(donationRepository.findById(Long.valueOf(id)));
			}
		}		
		if (errors.hasErrors()) {
			model.addAttribute("donations", donations);
			model.addAttribute("donationIDs", Donation.getDonationIDs(donations));
			model.addAttribute("payBillForm", payBillForm);
			model.addAttribute("totalBill", Donation.getTotal(donations));
			return "payments/paybill";
		}
    	Account account = accountRepository.findByEmail(principal.getName());    	
		CreditCardTransaction transaction = ccTransactionRepository.saveAndFlush(payBillForm.createCCTransaction(account));
		
		for (Donation donation: donations) {
			donation.setPaymentComplete(true);	
			donation.setCreditCardTransaction(transaction);
			Donation updatedDonation = donationRepository.update(donation);
		}	
		MessageHelper.addSuccessAttribute(ra, "payment.successful");    			
		
		return "redirect:/";		
	}

	
}
