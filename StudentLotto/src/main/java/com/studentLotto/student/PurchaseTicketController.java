package com.studentLotto.student;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
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
import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.support.web.MessageHelper;

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

	private static Student currStudent;
	private static Lottery currentLottery;
	private static int maxNumTickets;

	@RequestMapping(value = DISPLAY_PURCHASE_TICKET, method = RequestMethod.GET)
	public String displayPurchaseTicket(Principal principal, Model model,
			ModelAndView modelAndView, RedirectAttributes ra) {

		currStudent = getStudent();

		if (currStudent == null) {
			MessageHelper.addErrorAttribute(ra, "ticket.edit.anonymousStudent");
			return "redirect:/";
		}
		// get student id
		long studentId = currStudent.getId();
		// get university id
		long universityId = currStudent.getUniversity().getId();

		currentLottery = lotteryRepo.findUpcomingForUniversity(universityId);

		if (currentLottery == null) {
			// exit because there is no lottery set up for this student
			// university within the given date
			MessageHelper.addInfoAttribute(ra,
					"ticket.edit.unavailableLottery");
			return "redirect:/";
		}

		int lotteryId = currentLottery.getId();
		maxNumTickets = currentLottery.getMaxTicketsAllowedToPurchase();
		final int maxCountBalls = currentLottery.getNumberOfBallsPicked();
		final int maxNumberRange = currentLottery.getNumberOfBallsAvailable();

		// find out if the students already purchased tickets for that lottery
		// update the max number of tickets the student can purchase
		maxNumTickets = maxNumTickets
				- ticketRepo.findStudentPurchasedticketForLotteryCount(
						studentId, lotteryId);
		if (maxNumTickets <= 0) {
			// exit because the student already purchased the max allowable
			// tickets
			MessageHelper.addInfoAttribute(ra,
					"ticket.edit.maxNumTicketExceeded");
			return "redirect:/";
		}

		List<LotteryTicketForm> lotteryTicketFormList = new LinkedList<LotteryTicketForm>();
		// LotteryTicketForm[] lotteryTicketFormList = new
		// LotteryTicketForm[maxNumTickets];
		for (int i = 0; i < maxNumTickets; i++) {
			lotteryTicketFormList.add(new LotteryTicketForm());
			// lotteryTicketFormList[i]=new LotteryTicketForm();
		}
		// manufacture the number range for example the ball range is 1-55 in
		// power ball
		int[] numberRangeArr = new int[maxNumberRange];
		for (int i = 0; i < maxNumberRange; i++) {
			numberRangeArr[i] = i + 1;
		}
		// add ticket attributes
		TicketList ticketList = new TicketList(lotteryTicketFormList);
		model.addAttribute("maxNumTickets", maxNumTickets);
		model.addAttribute("maxCountBalls", maxCountBalls);
		model.addAttribute("numberRangeArr", numberRangeArr);
		model.addAttribute("lotteryTicketForm", new LotteryTicketForm());
		model.addAttribute("ticketList", ticketList);

		return principal != null ? PURCHASE_TICKET_VIEW_NAME : "redirect:/";
	}

	@RequestMapping(value = PROCESS_TICKET_PURCHASE, method = RequestMethod.POST)
	public String processTicketPurchase(HttpServletRequest request,
			Model model, @Valid @ModelAttribute TicketList ticketList,
			Errors errors, RedirectAttributes ra) {
		if (maxNumTickets <= 0) {
			// exit because the student already purchased the max allowable
			// tickets
			MessageHelper.addInfoAttribute(ra,
					"ticket.edit.maxNumTicketExceeded");
			return "redirect:/";
		}
		ArrayList<LotteryTicketForm> arrayTicketList = (ArrayList<LotteryTicketForm>) ticketList
				.getTicketList();

		if (errors.hasErrors()) {
			MessageHelper.addErrorAttribute(ra,
					"ticket.edit.errorDetected");
			return "redirect:/displayPurchaseTicket";
		}
		if (currStudent == null) {
			// exit student error!
			MessageHelper.addErrorAttribute(ra,
					"ticket.edit.anonymousStudent");
			return "redirect:/";
		}

		// currStudent = getStudent();
		long studentId = currStudent.getId();

		if (currentLottery == null) {
			// exit because there is no lottery set up for this student
			// university within the given date
			MessageHelper.addInfoAttribute(ra,
					"ticket.edit.unavailableLottery");
			return "redirect:/";
		}

		int lotteryId = currentLottery.getId();
		Iterator<LotteryTicketForm> it = arrayTicketList.iterator();
		int i = 0;
		while (it.hasNext()) {
			LotteryTicketForm form = it.next();

			// it means that this ticket was not configured
			if (form.getFirstNumber() == 0) {
				continue;
			}
			LotteryTicket persistentTicket = new LotteryTicket(form, studentId,
					currentLottery);

			ticketRepo.save(persistentTicket);
			i++;
		}
		return "redirect:/bill/payTicket";
	}

	private Student getStudent() {

		Object myPrincipal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (myPrincipal instanceof UserDetails) {
			userDetails = (UserDetails) myPrincipal;
		}
		String studentEmail = userDetails.getUsername();
		Student currentStudent = studentRepo.findByUEmailAddress(studentEmail);
		return currentStudent;

	}

}
