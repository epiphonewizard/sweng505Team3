package com.studentLotto.Administrator;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountEditForm;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.account.Person;
import com.studentLotto.account.PersonRepository;
import com.studentLotto.account.Student;
import com.studentLotto.account.StudentRepository;

@Controller
public class AdministratorController {

	 private AdministratorRepository AdministratorRepository;
	 String ADMIN_VIEW = "adminrole/adminrole";
	 String UPDATE_VIEW = "adminrole/updaterole";
	 
	 @Autowired
	    public AdministratorController(AdministratorRepository AdministratorRepository) {
	        this.AdministratorRepository = AdministratorRepository;
	     
	    }
	 
	 @RequestMapping(value= "adminrole")
	 public String ARole(Model model){
		 model.addAttribute("messageAdmin", AdministratorRepository.findByRole("ROLE_ADMIN"));
		 model.addAttribute("messageUser", AdministratorRepository.findByRole("ROLE_USER"));

		 return ADMIN_VIEW;
	 }
	 
	
	 @RequestMapping(value="updaterole", method = RequestMethod.GET)
	 public String updateAdmin(@RequestParam(value="rid") Long rid, Model model){
		 
	    	Account account = AdministratorRepository.findById(rid);
	    	AdministratorEditForm form = new AdministratorEditForm(account);
	    	model.addAttribute("id", form.getId());
	    	model.addAttribute("email", form.getEmail());
	    	model.addAttribute("role", form.getRole());
		 
		 return UPDATE_VIEW;
		 
	 }
	
	 
	 
}
