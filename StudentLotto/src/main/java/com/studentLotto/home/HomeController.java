package com.studentLotto.home;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.lottery.donation.DonationRepository;
import com.studentLotto.utilities.AccountActivation;
import com.studentLotto.utilities.AccountActivationRepository;
import com.studentLotto.utilities.AccountUtilities;

@Controller
public class HomeController {
	private final static int ACTIVE_ACCOUNT_STATUS = 1;
	
	private AccountActivationRepository accountActivationRepo;
	private AccountRepository accountRepository;
	private DonationRepository donationRepository;

	@Autowired
	public HomeController(AccountActivationRepository accountActivationRepo, AccountRepository accountRepository, DonationRepository donationRepository) {
		this.accountActivationRepo = accountActivationRepo;
		this.accountRepository = accountRepository;
		this.donationRepository = donationRepository;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		if (principal == null)
			return "redirect:/signin";
		else {
			Account account = accountRepository.findByEmail(principal.getName());
			model.addAttribute("donations", donationRepository.findForAccount(account.getId()));
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
