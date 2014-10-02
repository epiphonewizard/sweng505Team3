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
import com.studentLotto.support.mail.MailSenderImpl;
import com.studentLotto.support.mail.MessageCreator;
import com.studentLotto.support.web.*;
import com.studentLotto.utilities.AccountUtilities;

@Controller
public class SignupController {

	private static final String SIGNUP_VIEW_NAME = "signup/signup";

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute(new SignupForm());
		return SIGNUP_VIEW_NAME;
	}

	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		Account account = accountRepository.save(signupForm.createAccount());
		// Person person =personRepository.save(signupForm.createPerson());

		//emailActivation(account.getEmail(), account.getId());
		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html
		MessageHelper.addSuccessAttribute(ra, "signup.success");
		AccountUtilities au = new AccountUtilities();
		au.emailAccountActivation(account.getEmail(), account.getPassword());
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

	/*private void emailActivation(String emailAddress, Long acountId) {
		new MailSenderImpl().sendMail("sweng505team3@gmail.com", emailAddress,
				"Registration Activation",
				new MessageCreator().registrationValidationEmail(emailAddress));
	}*/

}
