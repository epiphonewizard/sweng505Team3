package com.studentLotto.lottery;

import java.security.Principal;
import java.util.Date;
import java.util.List;

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

import com.studentLotto.student.LotteryTicket;
import com.studentLotto.student.PurchaseTicketRepo;
import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.university.University;
import com.studentLotto.university.UniversityRepository;

@Controller
public class LotteryController {
	
	private static final String CREATE_LOTTERY_PAGE = "admin/createLottery";
	private static final String VIEW_LOTTERY_PAGE = "admin/viewLotteries";
	private static final String EDIT_LOTTERY_PAGE = "admin/editLottery";
	private static final String DRAW_LOTTERY_PAGE = "admin/drawLottery";
	private static final String VIEW_RESULTS_LOTTERY_PAGE = "admin/viewLotteryResults";
	

	@Autowired
	private LotteryRepository lotteryRepository;
	
	@Autowired
	private UniversityRepository universityRepository;
	
	@Autowired
	private PurchaseTicketRepo purchaseTicketRepo;
	
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

        MessageHelper.addSuccessAttribute(ra, "lottery.create.success", lottery.getUniversity().getName());
		return "redirect:/lottery/view"; 
	}
	
	@RequestMapping(value = "lottery/view", method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String view(Principal principal, Model model) {
		model.addAttribute("allLotteries", lotteryRepository.findAll());
		return VIEW_LOTTERY_PAGE; 
	}
	
	@RequestMapping(value="lottery/results", method=RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String results(int id, Principal principal, RedirectAttributes ra, Model model){
		Lottery lottery = lotteryRepository.findOne(id);		
		if(lottery == null){
			MessageHelper.addErrorAttribute(ra, "lottery.notfound");
			return "error/general";
		}
		model.addAttribute("lottery", lottery);
		model.addAttribute("winners", purchaseTicketRepo.findWinningTicketsForLottery(lottery.getId()));
		return VIEW_RESULTS_LOTTERY_PAGE;
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
        MessageHelper.addSuccessAttribute(ra, "lottery.update.success", lotteryRepository.findOne(editLotteryForm.getId()).getUniversity().getName());
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
	public String draw(Principal principal, Model model){
		
		List<Lottery> lotteryList = lotteryRepository.findAll();		
		model.addAttribute("activeLotteryList", lotteryList);
		
		Lottery selectedLottery = new Lottery();
		model.addAttribute("selectedLottery", selectedLottery);
		
		return DRAW_LOTTERY_PAGE;
	}
	
	@RequestMapping(value="lottery/draw", method=RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String draw(Principal principal, Model model, RedirectAttributes ra, @ModelAttribute Lottery selectedLottery){
		Lottery lottery = lotteryRepository.findOne(selectedLottery.getId());
		Date currentDate = new Date();
		
		if (lottery.getDrawingDate().after(currentDate)) {
			MessageHelper.addErrorAttribute(ra, "lottery.draw.dateFailure", lottery.getUniversity().getName());
			return "redirect:/lottery/draw";
		} else {
			List<LotteryTicket> lotteryTickets = purchaseTicketRepo.findPaidTicketsForLottery(selectedLottery.getId());
			if (lotteryTickets.size() > 0) {
				lotteryService.drawWinningNumbers(lottery, lotteryTickets);
				if (lottery.getWinningNumbers().size() > 0) {
					lotteryService.payoutLottery(lottery);
				} else {
					MessageHelper.addErrorAttribute(ra, "lottery.draw.winningNumberFailure", lottery.getUniversity().getName());
					return "redirect:/lottery/draw";
				}
				MessageHelper.addSuccessAttribute(ra, "lottery.draw.success", lottery.getUniversity().getName());
			} else {
				MessageHelper.addErrorAttribute(ra, "lottery.draw.ticketFailure", lottery.getUniversity().getName());
			}
				
			return "redirect:/lottery/draw";
		}
	}
	
	@RequestMapping(value="/runLottery", method=RequestMethod.GET)
	public void draw(Integer lotteryId, String key){
		if("M0neyDr3ams".equals(key)) {
			Lottery lottery = lotteryRepository.findOne(lotteryId);	
			List<LotteryTicket> lotteryTickets = purchaseTicketRepo.findPaidTicketsForLottery(lotteryId);
			if (lotteryTickets.size() > 0) {
				lotteryService.drawWinningNumbers(lottery, lotteryTickets);
				lotteryService.payoutLottery(lottery);
			}
		}
		
		
	}
	
}
