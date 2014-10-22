package com.studentLotto.account;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.support.web.MessageHelper;

@Controller
@Secured("ROLE_USER")
class AccountController {

    private AccountRepository accountRepository;
    private PersonRepository personRepositroy;
    private StudentRepository studentRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository, PersonRepository personRepository,
    		StudentRepository studentRepository) {
        this.accountRepository = accountRepository;
        this.personRepositroy = personRepository;
        this.studentRepository = studentRepository;
    }

    @RequestMapping(value = "account/current", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Account accounts(Principal principal) {
        Assert.notNull(principal);
        return accountRepository.findByEmail(principal.getName());
    }
    
    @RequestMapping(value= "account", method = RequestMethod.GET)
    public String details(Principal principal, Model model){
    	Assert.notNull(principal);
    	Account account = accountRepository.findByEmail(principal.getName());
    	Person person =  account.getPerson();
    	Student student = person.getStudent();
    	model.addAttribute("account", account);
    	model.addAttribute("person", person);
    	model.addAttribute("student", student);
    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    	model.addAttribute("birthDate", df.format(person.getBirthdate()));
    	return "account/details";
    }
    
    @RequestMapping(value= "account/edit", method = RequestMethod.GET)
    public String edit(Principal principal, Model model){
    	Assert.notNull(principal);
    	Account account = accountRepository.findByEmail(principal.getName());
    	Person person =  account.getPerson();
    	Student student = person.getStudent();
    	model.addAttribute("student", student);
    	AccountEditForm form = new AccountEditForm(person);
    	model.addAttribute(form);
    	
    	return "account/edit";
    } 
    
    @RequestMapping(value= "account/edit", method = RequestMethod.POST)
    public String edit(Principal principal, @Valid @ModelAttribute AccountEditForm accountEditForm,
			Errors errors, RedirectAttributes ra){
    	Assert.notNull(principal);
		if (errors.hasErrors()) {
			return "account/edit";
		}
    	Account current = accountRepository.findByEmail(principal.getName());
    	Person person = accountEditForm.getUpdatedPerson(current);
    	if(person != null){
	    	Student student = accountEditForm.getUpdatedStudent(person.getStudent());
	    	if(student != null){
	    		studentRepository.save(student);
	    	}
	    	personRepositroy.save(person);
    	}
		MessageHelper.addSuccessAttribute(ra, "account.edit.success");    	
    	return "redirect:/account";
    } 
}
