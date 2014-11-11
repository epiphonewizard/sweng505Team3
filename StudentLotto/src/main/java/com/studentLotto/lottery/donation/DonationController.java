package com.studentLotto.lottery.donation;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.university.University;
import com.studentLotto.university.UniversityRepository;

@Controller
public class DonationController {

	@Autowired
	private DonationRepository donateRepository;

	@Autowired
	private UniversityRepository universityRepository;
	@Autowired
	private AccountRepository accountRepository;
	

	@Autowired
	private LotteryRepository lotteryRepository;

	@RequestMapping(value="donate", method=RequestMethod.GET)
	public String donate(Model model){
		List<University> unis = universityRepository.getUniversityListForUpcomingLotteries();
		model.addAttribute("universityList", unis);
		model.addAttribute("donationForm", new DonationForm());
		
		return "student/donate";		
	}
	
	@RequestMapping(value="donate", method=RequestMethod.POST)
	public String donate(Principal principal, @Valid @ModelAttribute DonationForm donateForm, Errors errors, RedirectAttributes ra, Model model){
		if (errors.hasErrors()) {
			List<University> unis = universityRepository.getUniversityListForUpcomingLotteries();
			model.addAttribute("universityList", unis);
			return "student/donate";
		}

    	Account account = accountRepository.findByEmail(principal.getName());    	
		Lottery lottery = lotteryRepository.findUpcomingForUniversity(donateForm.getUniversityId());
		
		Donation donation = donateForm.createDonation(account, lottery);
		donateRepository.save(donation);				
		
		return "redirect:/bill/pay";		
	}
	
	@RequestMapping(value="donate/delete", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String deleteDonation(Principal principal, HttpServletRequest request){
		Assert.notNull(principal, "Invalid Permissions");
		Long donationId = Long.valueOf(request.getParameter("id"));
		Donation donation = donateRepository.findById(donationId);
		Assert.isTrue(donation.getAccount().getEmail().equals(principal.getName()), "Invalid Permissions");
		
		//If it gets to this point it is a valid request
		donateRepository.delete(donation);
		return "{\"success\": true}"; 
		
	}
	
}
