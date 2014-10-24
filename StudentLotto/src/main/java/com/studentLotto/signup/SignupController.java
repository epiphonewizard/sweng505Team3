package com.studentLotto.signup;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.*;
import com.studentLotto.support.web.*;
import com.studentLotto.university.UniversityRepository;
import com.studentLotto.utilities.AccountActivation;
import com.studentLotto.utilities.AccountActivationRepository;
import com.studentLotto.utilities.AccountUtilities;

@Controller
public class SignupController {

	private static final String SIGNUP_VIEW_NAME = "signup/signup";

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AccountActivationRepository accountActivationRepo;

	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute("allSchools", universityRepository.getUniversityList());
		model.addAttribute(new SignupForm());
		return SIGNUP_VIEW_NAME;
	}

	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm,
			Errors errors, RedirectAttributes ra, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("allSchools", universityRepository.getUniversityList());
			return SIGNUP_VIEW_NAME;
		}
		Account account = accountRepository.save(signupForm.createAccount());
		// Person person =personRepository.save(signupForm.createPerson());

		// emailActivation(account.getEmail(), account.getId());
		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html
		MessageHelper.addSuccessAttribute(ra, "signup.success");
		AccountUtilities accountUtilities = new AccountUtilities();

		AccountActivation accountActivation = accountUtilities
				.emailAccountActivation(account.getEmail(),
						account.getPassword(), accountActivationRepo);
		if (accountActivation != null) {
			accountActivationRepo.save(accountActivation);
		}

		return "redirect:/signin";
	}

	/**
	 * refrerence data used in creation of page. This will need to be updated
	 * for real values once database is completely set up.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap();

		Map<String, String> countries = new LinkedHashMap<String, String>();
		countries.put("US", "United Stated");
		countries.put("CHINA", "China");
		countries.put("SG", "Singapore");
		countries.put("MY", "Malaysia");
		referenceData.put("countryList", countries);

		Map<String, String> states = new LinkedHashMap<String, String>();
		states.put("OR", "Oregon");
		states.put("CA", "California");
		states.put("PA", "Pennyslvannia");
		referenceData.put("stateList", states);

		Map<String, String> schools = new LinkedHashMap<String, String>();
		schools.put("PennState", "Penn State");
		schools.put("OregonState", "Oregon State");
		schools.put("USC", "University of Southern California");
		referenceData.put("schoolList", schools);

		return referenceData;
	}

}
