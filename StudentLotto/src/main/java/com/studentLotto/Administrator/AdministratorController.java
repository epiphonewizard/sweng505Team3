package com.studentLotto.Administrator;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.studentLotto.account.UserService;
import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.university.University;
import com.studentLotto.university.UniversityForm;

@Controller
@Secured("ROLE_ADMIN")

public class AdministratorController {

	 private AdministratorRepository AdministratorRepository;
	 String ADMIN_VIEW = "adminrole/adminrole";
	 String UPDATE_VIEW = "adminrole/updaterole";
	 
	 @Autowired
	    public AdministratorController(AdministratorRepository AdministratorRepository) {
	        this.AdministratorRepository = AdministratorRepository;
	     
	    }
	 @Autowired
		private UserService userService;
	 
	 @RequestMapping(value= "adminrole")
	 public String ARole(Model model){
		 model.addAttribute("messageAdmin", AdministratorRepository.findByRole("ROLE_ADMIN"));
		 model.addAttribute("messageUser", AdministratorRepository.findByRole("ROLE_USER"));

		 return ADMIN_VIEW;
	 }
	 
	
	 @RequestMapping(value="updaterole", method = RequestMethod.GET)
	 public String index(Principal principal, @RequestParam(value="rid") Long rid, Model model){
		 
	    	Account account = AdministratorRepository.findById(rid);
	    	AdministratorEditForm updateroleForm = new AdministratorEditForm(account);
	    	model.addAttribute("updateroleForm",updateroleForm);
	    	
		 return principal != null ? UPDATE_VIEW : "redirect:/signin";
		 
	 }
	 
	
	
	 
	 @RequestMapping(value = "updaterole", method = RequestMethod.POST)
		public String index(@Valid @ModelAttribute AdministratorEditForm updateroleForm,
				Errors errors, Model model) {
		
		 if (errors.hasErrors()) {
				return UPDATE_VIEW;
			}
		 	Account account = updateroleForm.createAccount();
				/*AdministratorRepository.update(account);
				model.addAttribute(new AdministratorEditForm(account));
				MessageHelper.addSuccessAttribute(ra, "updaterole.success", account.getEmail());*/
				//return "redirect:/updaterole?rid=" + account.getId();
				return ADMIN_VIEW;
			
		}
	 
	
	 
	 
}
