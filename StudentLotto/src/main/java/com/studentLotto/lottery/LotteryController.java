package com.studentLotto.lottery;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.university.University;
import com.studentLotto.university.UniversityRepository;

@Controller
public class LotteryController {
	
	private static final String CREATE_LOTTERY_PAGE = "admin/createLottery";
	private static final String VIEW_LOTTERY_PAGE = "admin/viewLotteries";
	private static final String EDIT_LOTTERY_PAGE = "admin/editLottery";
	

	@Autowired
	private LotteryRepository lotteryRepository;
	
	@Autowired
	private UniversityRepository universityRepository;
	
	@Autowired
	private LotteryService lotteryService;
	
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
		Lottery lottery = createLotteryForm.createLottery(university);	
		lotteryRepository.save(lottery);

        MessageHelper.addSuccessAttribute(ra, "lottery.create.successful");
		return "redirect:/"; 
	}
	
	@RequestMapping(value = "lottery/view", method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String view(Principal principal, Model model) {
		model.addAttribute("allLotteries", lotteryRepository.findAll());
		return VIEW_LOTTERY_PAGE; 
	}
	
	@RequestMapping(value="lottery/edit", method=RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String edit(int id, Principal principal, Model model){
		model.addAttribute("allSchools", universityRepository.getUniversityList());
		model.addAttribute(new EditLotteryForm(lotteryRepository.findOne(id))); 
		
		return EDIT_LOTTERY_PAGE;
	}
	
	@RequestMapping(value = "lottery/edit", method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String edit(@Valid @ModelAttribute EditLotteryForm editLotteryForm, Errors errors, RedirectAttributes ra, Model model) {
		if (errors.hasErrors()) {
			return EDIT_LOTTERY_PAGE;
		}
		lotteryService.editLottery(editLotteryForm);
        MessageHelper.addSuccessAttribute(ra, "lottery.create.successful");
		return "redirect:/lottery/view"; 
	}
	
	@RequestMapping(value="lottery/delete", method=RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String delete(int id, Principal principal, Model model){
		lotteryService.deleteLottery(id);
		return "redirect:/lottery/view"; 
	}
	
	@RequestMapping(value="lottery/draw", method=RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String draw(Principal principal, Model model, RedirectAttributes ra, int lotteryId){
		Lottery lottery = lotteryRepository.findOne(lotteryId);
		lotteryService.drawWinningNumbers(lottery);
		return VIEW_LOTTERY_PAGE;
	}
	
}
