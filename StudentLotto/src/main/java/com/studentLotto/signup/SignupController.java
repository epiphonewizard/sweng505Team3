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
import com.studentLotto.utilities.AccountUtilities;

@Controller
public class SignupController {

	private static final String SIGNUP_VIEW_NAME = "signup/signup";

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private UniversityRepository universityRepository;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute(new SignupForm());
		model.addAttribute("allSchools", universityRepository.findAll());
		return SIGNUP_VIEW_NAME;
	}

	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		
		Account account = null;
		
		if("Student".equals(signupForm.getUserType())) {
			account = createStudentAccount(signupForm);
		} else {
			account = createDonorAccount(signupForm);
		}
	
		// see /WEB-INF/i18n/messages.properties and
		// /WEB-INF/views/homeSignedIn.html
		MessageHelper.addSuccessAttribute(ra, "signup.success");
		AccountUtilities au = new AccountUtilities();
		au.emailAccountActivation(account.getEmail(), account.getPassword());
		return "redirect:/signin";
	}
	
	private Account createDonorAccount(SignupForm signupForm) {
		Person person = personRepository.save(new Person(signupForm.getDateOfBirth(), signupForm.getFirstName(), signupForm.getLastName(), signupForm.getHomeCity(), signupForm.getHomeStreetAddress(), "", signupForm.getHomeState(), signupForm.getHomeZip(), signupForm.getPhoneNumber(), new Account(signupForm.getEmail(), signupForm.getPassword())));
		return person.getAccount();
	}
	
	private Account createStudentAccount(SignupForm signupForm) {
		University university = universityRepository.findOne(signupForm.getSchool());
		Student student = studentRepository.save(new Student(signupForm.getMailCity(), signupForm.getMailStreetAddress(), "", signupForm.getMailState(), signupForm.getHomeZip(), signupForm.getEmail(), new Person(signupForm.getDateOfBirth(), signupForm.getFirstName(), signupForm.getLastName(), signupForm.getHomeCity(), signupForm.getHomeStreetAddress(), "", signupForm.getHomeState(), signupForm.getHomeZip(), signupForm.getPhoneNumber(), new Account(signupForm.getEmail(), signupForm.getPassword())), university));
		return student.getPerson().getAccount();
	}

	/**
	 * refrerence data used in creation of page. This will need to be updated
	 * for real values once database is completely set up.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Map<String,LinkedHashMap<String, String>> referenceData(HttpServletRequest request) throws Exception {
		Map<String,LinkedHashMap<String, String>> referenceData = new HashMap<String,LinkedHashMap<String, String>>();

		LinkedHashMap<String, String> countries = new LinkedHashMap<String, String>();
		countries.put("US", "United Stated");
		countries.put("CHINA", "China");
		countries.put("SG", "Singapore");
		countries.put("MY", "Malaysia");
		referenceData.put("countryList", countries);

		LinkedHashMap<String, String> states = new LinkedHashMap<String, String>();
		states.put("OR", "Oregon");
		states.put("CA", "California");
		states.put("PA", "Pennyslvannia");
		referenceData.put("stateList", states);

		LinkedHashMap<String, String> schools = new LinkedHashMap<String, String>();
		schools.put("PennState", "Penn State");
		schools.put("OregonState", "Oregon State");
		schools.put("USC", "University of Southern California");
		referenceData.put("schoolList", schools);

		return referenceData;
	}
}
