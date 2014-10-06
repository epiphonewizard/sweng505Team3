package com.studentLotto.passwordreset;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.*;
import com.studentLotto.support.mail.MailSenderImpl;
import com.studentLotto.support.mail.MessageCreator;
import com.studentLotto.support.web.*;
import com.studentLotto.timedemail.TimedEmailCode;
import com.studentLotto.timedemail.TimedEmailCodeRepository;

@Controller
public class PasswordResetController {

    private static final String PASSWORD_RESET_VIEW_NAME = "password/passwordReset";
    private static final String PASSWORD_RESET_CHANGE_VIEW_NAME = "password/change";	
	
	@Autowired
	private AccountRepository accountRepository; 
	
	@Autowired
	private TimedEmailCodeRepository timedEmailCodeRepository;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "passwordReset")
	public String passwordReset(Model model) {
		model.addAttribute(new PasswordResetForm());
        return PASSWORD_RESET_VIEW_NAME;
	}
	
	@RequestMapping(value = "passwordReset", method = RequestMethod.POST)
	public String passwordReset(@Valid @ModelAttribute PasswordResetForm passwordResetForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return PASSWORD_RESET_VIEW_NAME;
		}
		Account account = accountRepository.findByEmail(passwordResetForm.getEmail());
		if(account != null){
			String guid = createNewGUID();
			Date currentDate = new Date();
			TimedEmailCode timedEmailCode = new TimedEmailCode(account.getEmail(), guid, currentDate);
			timedEmailCodeRepository.save(timedEmailCode);	
			
			sendResetEmail(account.getEmail(), guid.toString());
		}

        MessageHelper.addSuccessAttribute(ra, "email.reset");
		return "redirect:/signin"; 
	}

	private String createNewGUID() {
		String guid =  UUID.randomUUID().toString();
		if(timedEmailCodeRepository.findByGuid(guid) == null){
			return guid;
		}else{
			return createNewGUID();
		}
	}
	
	private void sendResetEmail(String emailAddress, String guid){
		new MailSenderImpl().sendMail("sweng505team3@gmail.com", emailAddress, 
				"Reset Password", 
				new MessageCreator().passwordResetEmail(emailAddress, guid));
	}	

	@RequestMapping(value = "passwordReset/change")
	public String passwordReset(@RequestParam String id, @RequestParam String email, ModelMap model, RedirectAttributes ra) {
		TimedEmailCode emailCode = timedEmailCodeRepository.findByGuid(id);
		if(emailCode == null || !emailCode.safeToUse(email)){
	        MessageHelper.addErrorAttribute(ra, "email.expired");
	        return "redirect:/signin";
		}
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		
		session.setAttribute("email", email);
		session.setAttribute("guid", id);
		ChangePasswordForm passwordForm = new ChangePasswordForm();
		
		model.addAttribute(passwordForm);
		
        return PASSWORD_RESET_CHANGE_VIEW_NAME;
	}
	
	@RequestMapping(value = "passwordReset/change", method = RequestMethod.POST)
	public String passwordReset(@Valid @ModelAttribute ChangePasswordForm changePasswordForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return PASSWORD_RESET_CHANGE_VIEW_NAME;
		}
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		
		Account account = accountRepository.findByEmail(session.getAttribute("email").toString());
		TimedEmailCode emailCode = timedEmailCodeRepository.findByGuid(session.getAttribute("guid").toString());				
		
		if(account != null && emailCode != null && emailCode.safeToUse(account.getEmail())){
			//Lets do this tomorrow
			account = accountRepository.changePassword(account, changePasswordForm.getPassword());			
	        MessageHelper.addSuccessAttribute(ra, "password.changed");
		}else{
			MessageHelper.addErrorAttribute(ra, "email.expired");
		}
		return "redirect:/signin"; 
	}
}
