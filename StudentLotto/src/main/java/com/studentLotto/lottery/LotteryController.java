package com.studentLotto.lottery;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.Account;
import com.studentLotto.passwordreset.PasswordResetForm;
import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.timedemail.TimedEmailCode;
import com.studentLotto.university.University;
import com.studentLotto.university.UniversityRepository;

@Controller
public class LotteryController {
	
	private static final String CREATE_LOTTERY_PAGE = "admin/createLottery";

	@Autowired
	private LotteryRepository lotteryRepository;
	
	@Autowired
	private UniversityRepository universityRepository;
	
	@RequestMapping(value="lottery/create", method=RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String create(Principal principal, Model model){
		model.addAttribute("allSchools", universityRepository.getUniversityList());
		model.addAttribute(new CreateLotteryForm());
		
		return CREATE_LOTTERY_PAGE;
	}
	
	@RequestMapping(value = "lottery/create", method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String create(@Valid @ModelAttribute CreateLotteryForm createLotteryForm, Errors errors, RedirectAttributes ra, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("allSchools", universityRepository.getUniversityList());
			return CREATE_LOTTERY_PAGE;
		}
		University university = universityRepository.findById(createLotteryForm.getUniversityID());
		Lottery lottery = createLotteryForm.newLottery(university);	
		lotteryRepository.save(lottery);

        MessageHelper.addSuccessAttribute(ra, "lottery.create.successful");
		return "redirect:/"; 
	}
	
	
}
