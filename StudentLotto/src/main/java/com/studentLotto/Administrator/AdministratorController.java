package com.studentLotto.Administrator;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.account.AccountService;
import com.studentLotto.account.UserService;

@Controller
@Secured("ROLE_ADMIN")
public class AdministratorController {

	private static final String ADMIN_VIEW = "adminrole/adminrole";
	private static final String UPDATE_VIEW = "adminrole/updaterole";
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "adminrole")
	public String showUserRoles(Model model) {
		model.addAttribute("adminUsers", accountRepository.findByRole("ROLE_ADMIN"));
		model.addAttribute("generalUsers", accountRepository.findByRole("ROLE_USER"));

		return ADMIN_VIEW;
	}

	@RequestMapping(value = "updaterole", method = RequestMethod.GET)
	public String updateRole(Principal principal,
			@RequestParam(value = "rid") Long rid, Model model) {

		Account account = accountRepository.findById(rid);
		AdministratorEditForm updateroleForm = new AdministratorEditForm(account);
		model.addAttribute("updateroleForm", updateroleForm);

		return principal != null ? UPDATE_VIEW : "redirect:/signin";

	}

	@RequestMapping(value = "updaterole", method = RequestMethod.POST)
	public String updateRole(
			@Valid @ModelAttribute AdministratorEditForm administratorEditForm,
			Errors errors, Model model) {

		if (errors.hasErrors()) {
			return UPDATE_VIEW;
		}
		accountService.updateAccountRole(administratorEditForm.getId(), administratorEditForm.getRole());
		 
		model.addAttribute("adminUsers",accountRepository.findByRole("ROLE_ADMIN"));
		model.addAttribute("generalUsers", accountRepository.findByRole("ROLE_USER"));
		
		return ADMIN_VIEW;

	}
	
	@RequestMapping(value = "deleteUser", method = RequestMethod.GET)
	public String deleteUser(Long id, Model model) {

		accountService.deleteUser(id);
		
		model.addAttribute("adminUsers",accountRepository.findByRole("ROLE_ADMIN"));
		model.addAttribute("generalUsers", accountRepository.findByRole("ROLE_USER"));
		
		return ADMIN_VIEW;

	}

}
