package com.studentLotto.student;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.Student;
import com.studentLotto.account.StudentRepository;
import com.studentLotto.account.UserService;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.university.UniversityForm;

@Controller
@Secured("ROLE_ADMIN")
public class PurchaseTicketController {
	private static final String DISPLAY_PURCHASE_TICKET = "displayPurchaseTicket";
	private static final String PROCESS_TICKET_PURCHASE = "processTicketPurchase";
	private static final String PURCHASE_TICKET_VIEW_NAME = "student/purchaseTicket";

	@Autowired
	private PurchaseTicketRepo ticketRepo;

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private LotteryRepository lotteryRepo;

	@RequestMapping(value = DISPLAY_PURCHASE_TICKET, method = RequestMethod.GET)
	public String displayPurchaseTicket(Principal principal, Model model,
			ModelAndView modelAndView, HttpServletRequest request) {
		final int maxNumTickets = 5;
		int maxCountBalls = 5;
		int maxNumberRange = 20;
		List<LotteryTicketForm> lotteryTicketFormList = new LinkedList<LotteryTicketForm>();
		// LotteryTicketForm[] lotteryTicketFormList = new
		// LotteryTicketForm[maxNumTickets];
		for (int i = 0; i < maxNumTickets; i++) {
			lotteryTicketFormList.add(new LotteryTicketForm());
			// lotteryTicketFormList[i]=new LotteryTicketForm();
		}

		int[] numberRangeArr = new int[maxNumberRange];
		for (int i = 0; i < maxNumberRange; i++) {
			numberRangeArr[i] = i + 1;
		}
		// System.out.println(lotteryTicketFormList.size()+"Z");
		// modelAndView.addObject(maxNumTickets);
		TicketList ticketList = new TicketList(lotteryTicketFormList);
		model.addAttribute("maxNumTickets", maxNumTickets);
		model.addAttribute("maxCountBalls", maxCountBalls);
		model.addAttribute("numberRangeArr", numberRangeArr);

		model.addAttribute("lotteryTicketForm", new LotteryTicketForm());
		model.addAttribute("ticketList", ticketList);

		return principal != null ? PURCHASE_TICKET_VIEW_NAME
				: "redirect:/signin";
	}

	@RequestMapping(value = PROCESS_TICKET_PURCHASE, method = RequestMethod.POST)
	public String processTicketPurchase(Model model,
			@Valid @ModelAttribute TicketList ticketList, Errors errors,
			RedirectAttributes ra) {

		ArrayList<LotteryTicketForm> arrayTicketList = (ArrayList<LotteryTicketForm>) ticketList
				.getTicketList();
		System.out.println("XXXXX" + arrayTicketList.size());
		for (int i = 0; i < arrayTicketList.size(); i++) {
			System.out.println(arrayTicketList.get(i).toString());
		}
		if (errors.hasErrors()) {
			System.out.println("ERROR FOUND");
			return "redirect:/displayPurchaseTicket";
		}

		Object myPrincipal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (myPrincipal instanceof UserDetails) {
			userDetails = (UserDetails) myPrincipal;
		}
		String studentEmail = userDetails.getUsername();
		System.out.println(studentEmail + "   " + studentRepo);

		Student currentStudent = studentRepo.findByUEmailAddress(studentEmail);
		System.out.println(currentStudent);
		long studentId = currentStudent.getId();
		System.out.println(studentId);
		// Lottery upcomingLottery = lotteryRepo.getNextLottery();

		int lotteryId = 12;
		Iterator<LotteryTicketForm> it = arrayTicketList.iterator();
		int i = 0;
		while (it.hasNext()) {
			LotteryTicketForm form = it.next();
			System.out.println(form.getFirstNumber() + "   X" + i);
			// it means that this ticket was not configured
			if (form.getFirstNumber() == 0) {
				continue;
			}
			LotteryTicket persistentTicket = new LotteryTicket(form, studentId,
					lotteryId);
			System.out.println(persistentTicket.getFirstNumber() + "   X"
					+ ticketRepo);
			ticketRepo.save(persistentTicket);
			i++;
		}
		return "redirect:/signin";
	}

}
