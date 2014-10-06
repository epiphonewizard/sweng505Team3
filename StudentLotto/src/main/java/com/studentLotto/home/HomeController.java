package com.studentLotto.home;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.utilities.AccountActivation;
import com.studentLotto.utilities.AccountActivationRepository;

@Controller
public class HomeController {
	private final static int ACTIVE_ACCOUNT_STATUS = 1;

	@Autowired
	private AccountActivationRepository accountActivationRepo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal) {
		return principal != null ? "home/homeSignedIn" : "redirect:/signin";
	}

	@RequestMapping(value = "activation", method = RequestMethod.GET)
	public String activation(HttpServletRequest request, RedirectAttributes ra) {

		String id = request.getParameter("id");
		String email = request.getParameter("email");
		AccountActivation accountActivation = accountActivationRepo
				.findByEmail(email.trim());
		
		if (accountActivation.getCode().equals(id)) {
			accountActivationRepo.updateActivationStatus(accountActivation, ACTIVE_ACCOUNT_STATUS);
			MessageHelper.addSuccessAttribute(ra, "email.activate");
		}
		return "redirect:/signin";
	}
	
}
