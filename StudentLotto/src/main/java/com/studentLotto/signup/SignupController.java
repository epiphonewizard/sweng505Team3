package com.studentLotto.signup;

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
	private StudentRepository studentRepository;


	@Autowired
	private UserService userService;

	@Autowired
	private AccountActivationRepository accountActivationRepo;
	
	@Autowired
	private UniversityRepository universityRepository;

	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute(new SignupForm());
		model.addAttribute("schools", universityRepository.findAll());
		return SIGNUP_VIEW_NAME;
	}

	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		
		Account account = createAccount(signupForm);
		// Person person =personRepository.save(signupForm.createPerson());

		// emailActivation(account.getEmail(), account.getId());
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

	private Account createAccount(SignupForm signupForm) {
		if(signupForm.getUserType().equals("Donator")) {
			return personRepository.save(new Person(signupForm.getDateOfBirth(),
												  	signupForm.getFirstName(),
												  	signupForm.getLastName(),
												  	signupForm.getHomeCity(),
												  	signupForm.getHomeStreetAddress(),
												  	"",
												  	signupForm.getHomeState(),
												  	signupForm.getHomeZip(),
												  	signupForm.getPhoneNumber(),
												  	accountRepository.save(new Account(signupForm.getEmail(), 
															    signupForm.getPassword())))).getAccount();
		} else {
			Person person = personRepository.save(new Person(signupForm.getDateOfBirth(),
				  	signupForm.getFirstName(),
				  	signupForm.getLastName(),
				  	signupForm.getHomeCity(),
				  	signupForm.getHomeStreetAddress(),
				  	"",
				  	signupForm.getHomeState(),
				  	signupForm.getHomeZip(),
				  	signupForm.getPhoneNumber(),
				  	accountRepository.save(new Account(signupForm.getEmail(), 
							    signupForm.getPassword()))));
			return     studentRepository.save(new Student(signupForm.getMailCity(),
									 	 signupForm.getMailStreetAddress(),
									 	 "",
									 	 signupForm.getMailState(),
									 	 signupForm.getMailZip(),
									 	 signupForm.getEmail(),
									 	 universityRepository.findOne(signupForm.getSchool()),person)).getPerson().getAccount();
		}
	}
}
