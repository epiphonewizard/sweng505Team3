package com.studentLotto.home;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.account.Person;
import com.studentLotto.account.Student;
import com.studentLotto.account.UserService;
import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.lottery.LotteryService;
import com.studentLotto.lottery.donation.Donation;
import com.studentLotto.lottery.donation.DonationRepository;
import com.studentLotto.student.LotteryTicket;
import com.studentLotto.student.PurchaseTicketRepo;
import com.studentLotto.utilities.AccountActivation;
import com.studentLotto.utilities.AccountActivationRepository;
import com.studentLotto.utilities.AccountUtilities;

@Controller
public class HomeController {
	private final static int ACTIVE_ACCOUNT_STATUS = 1;
	
	private AccountActivationRepository accountActivationRepo;
	private AccountRepository accountRepository;
	private DonationRepository donationRepository;
	private LotteryRepository lotteryRepository;
	private PurchaseTicketRepo purchaseTicketRepo;
	
	@Autowired
	private LotteryService lotteryService;

	@Autowired
	public HomeController(AccountActivationRepository accountActivationRepo, AccountRepository accountRepository, DonationRepository donationRepository,
			LotteryRepository lotteryRepository, PurchaseTicketRepo purchaseTicketRepo) {
		this.accountActivationRepo = accountActivationRepo;
		this.accountRepository = accountRepository;
		this.donationRepository = donationRepository;
		this.lotteryRepository = lotteryRepository;
		this.purchaseTicketRepo = purchaseTicketRepo;
		
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		if (principal == null)
			return "redirect:/signin";
		else {
			Account account = accountRepository.findByEmail(principal.getName());
			List<Donation> donations = donationRepository.findForAccount(account.getId());
			
			if(donations.size() > 0)
				model.addAttribute("donations", donations);
			
			Person person = account.getPerson();
			if(person != null){
				Student student = person.getStudent();
				model.addAttribute("student", student);
				if(student != null){
					Lottery lottery = lotteryRepository.findUpcomingForUniversity(person.getStudent().getUniversity().getId());
					model.addAttribute("lottery", lottery);
					if(lottery != null){
						List<LotteryTicket> tickets = purchaseTicketRepo.findStudentTicketsForUpcomingLottery(student.getId(), lottery.getId());
						model.addAttribute("tickets", tickets);
						model.addAttribute("canPurchase", lottery.canPurchase());
						model.addAttribute("ticketSize", tickets.size());
						model.addAttribute("maxTicketsAllowedToPurchase", lottery.getMaxTicketsAllowedToPurchase());
						model.addAttribute("lotteryPool", lotteryRepository.calculateLotteryWinnings(lottery));
					}
					model.addAttribute("winners", purchaseTicketRepo.findWinningTicketsForStudent(student.getId()));
				}else{
					model.addAttribute("ticketSize", 0);
					model.addAttribute("maxTicketsAllowedToPurchase", 0);
				}
			}
			return "home/homeSignedIn";
		}
	}

	@RequestMapping(value = "activation", method = RequestMethod.GET)
	public String activation(HttpServletRequest request, RedirectAttributes ra) {
		// get the activation code and the email address
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		// Fetch the account activation info from the database
		AccountActivation accountActivation = accountActivationRepo
				.findByEmail(email.trim());
		// if the same info in the database match, then set the activation
		// status to "ACCOUNT ACTIVE"
		if (accountActivation.getCode().equals(id)) {
			accountActivationRepo.updateActivationStatus(accountActivation,
					ACTIVE_ACCOUNT_STATUS);
			AccountUtilities accountUtilities = new AccountUtilities();
			accountUtilities.emailAccountActivationSuccessful(email);

		}
		// redirect the user to sign in!
		return "redirect:/signin";
	}
	
}
