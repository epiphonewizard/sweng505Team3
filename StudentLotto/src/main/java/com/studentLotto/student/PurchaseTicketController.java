package com.studentLotto.student;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.studentLotto.account.Student;
import com.studentLotto.account.StudentRepository;
import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.lottery.LotteryService;

import com.studentLotto.support.web.MessageHelper;

@Controller
@Secured("ROLE_ADMIN")
public class PurchaseTicketController {
	private static final String DISPLAY_PURCHASE_TICKET = "displayPurchaseTicket";
	private static final String PROCESS_TICKET_PURCHASE = "processTicketPurchase";
	private static final String PURCHASE_TICKET_VIEW_NAME = "student/purchaseTicket";

	private final static int UNIQUE_TICKET_STATUS = 0;
	private final static int FOUND_DUPLICATE_WITHIN_CURRENT_PURCHASE = 1;
	private final static int FOUND_DUPLICATE_FROM_PREVIOUS_PURCHASE = 2;

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

		currStudent = getStudent(principal);

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
			MessageHelper
					.addInfoAttribute(ra, "ticket.edit.unavailableLottery");
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
			MessageHelper.addErrorAttribute(ra, "ticket.edit.errorDetected");

			return "redirect:/displayPurchaseTicket";
		}

		if (currStudent == null) {
			// exit student error!
			MessageHelper.addErrorAttribute(ra, "ticket.edit.anonymousStudent");
			return "redirect:/";
		}

		if (currentLottery == null) {
			// exit because there is no lottery set up for this student
			// university within the given date
			MessageHelper
					.addInfoAttribute(ra, "ticket.edit.unavailableLottery");
			return "redirect:/";
		}

		// get the duplicate status
		int duplicateStatus = getDuplicateStatus(arrayTicketList,
				currentLottery.getNumberOfBallsPicked(), currStudent.getId(),
				currentLottery.getId());

		if (duplicateStatus == FOUND_DUPLICATE_WITHIN_CURRENT_PURCHASE) {
			MessageHelper.addInfoAttribute(ra, "ticket.edit.duplicateInCart");
			return "redirect:/displayPurchaseTicket";
		} else if (duplicateStatus == FOUND_DUPLICATE_FROM_PREVIOUS_PURCHASE) {
			MessageHelper.addInfoAttribute(ra,
					"ticket.edit.duplicateFromPreviousPurchase");
			return "redirect:/displayPurchaseTicket";
		} else {
			// all is good
		}

		Iterator<LotteryTicketForm> it = arrayTicketList.iterator();

		while (it.hasNext()) {
			LotteryTicketForm form = it.next();

			// it means that this ticket was not configured
			if (form.getFirstNumber() == 0) {
				continue;
			}
			LotteryTicket persistentTicket = new LotteryTicket(form,
					currStudent, currentLottery);

			ticketRepo.save(persistentTicket);
		}
		return "redirect:/bill/payTicket";
	}

	private Student getStudent(Principal principal) {
		String studentEmail = principal.getName();
		Student currentStudent = studentRepo.findByUEmailAddress(studentEmail);
		return currentStudent;

	}

	@RequestMapping(value = "ticket/delete", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String deleteDonation(Principal principal,
			HttpServletRequest request) {
		Assert.notNull(principal, "Invalid Permissions");
		Long ticketId = Long.valueOf(request.getParameter("id"));
		LotteryTicket ticket = ticketRepo.findById(ticketId);

		// get current signed in student info
		Student student = studentRepo.findByUEmailAddress(principal.getName());

		Assert.isTrue(ticket.getStudent().getId() == student.getId(),
				"Invalid Permissions");

		// If it gets to this point it is a valid request
		ticketRepo.delete(ticket);
		return "{\"success\": true}";

	}

	private int getDuplicateStatus(ArrayList<LotteryTicketForm> arrayFormList,
			int ballCount, long studentId, int lotteryId) {

		int duplicateStatus = UNIQUE_TICKET_STATUS;
		TreeSet<Integer> firstTicketCompare = null;
		TreeSet<Integer> secondTicketCompare = null;
		int matchCount = 0;
		// if we only have 1 ticket. We dont have other tickets to compare
		// against
		if (arrayFormList.size() > 1) {

			// Step 1: I will compare the "cart" tickets to find out if they
			// match
			// or not
			for (int i = 0; i < arrayFormList.size(); i++) {
				// get the first ticket sorted
				firstTicketCompare = (TreeSet<Integer>) LotteryService
						.convertTicketToSortedSet(arrayFormList.get(i)
								.getFirstNumber(), arrayFormList.get(i)
								.getSecondNumber(), arrayFormList.get(i)
								.getThirdNumber(), arrayFormList.get(i)
								.getFourthNumber(), arrayFormList.get(i)
								.getFifthNumber(), arrayFormList.get(i)
								.getSixthNumber(), ballCount);
				for (int j = i+1; j < arrayFormList.size(); j++) {
					// get the second ticket sorted
					secondTicketCompare = (TreeSet<Integer>) LotteryService
							.convertTicketToSortedSet(arrayFormList.get(j)
									.getFirstNumber(), arrayFormList.get(j)
									.getSecondNumber(), arrayFormList.get(j)
									.getThirdNumber(), arrayFormList.get(j)
									.getFourthNumber(), arrayFormList.get(j)
									.getFifthNumber(), arrayFormList.get(j)
									.getSixthNumber(), ballCount);
					// now compare both tickets
					matchCount = LotteryService.getMatchingNumberCount(
							firstTicketCompare, secondTicketCompare);
					// that means all balls matched on both tickets! so it is a
					// duplicate ticket
					//System.out.println(firstTicketCompare.toString() +" Y " +secondTicketCompare.toString()+ "  "+matchCount+"  "+ballCount);
					if (matchCount == ballCount) {

						return FOUND_DUPLICATE_WITHIN_CURRENT_PURCHASE;
					}
				}// end inner loop
			}// end outer loop
		}
		// step 2: I will compare the "cart" ticket to the previously
		// purchased tickets to find out if a the student is re purchasing
		// the same ticket
		ArrayList<LotteryTicket> purchasedTickets = (ArrayList<LotteryTicket>) ticketRepo
				.findStudentTicketsForUpcomingLottery(studentId, lotteryId);
		for (int i = 0; i < purchasedTickets.size(); i++) {

			firstTicketCompare = (TreeSet<Integer>) LotteryService
					.convertTicketToSortedSet(purchasedTickets.get(i)
							.getFirstNumber(), purchasedTickets.get(i)
							.getSecondNumber(), purchasedTickets.get(i)
							.getThirdNumber(), purchasedTickets.get(i)
							.getFourthNumber(), purchasedTickets.get(i)
							.getFifthNumber(), purchasedTickets.get(i)
							.getSixthNumber(), ballCount);
			for (int j = 0; j < arrayFormList.size(); j++) {
				secondTicketCompare = (TreeSet<Integer>) LotteryService
						.convertTicketToSortedSet(arrayFormList.get(j)
								.getFirstNumber(), arrayFormList.get(j)
								.getSecondNumber(), arrayFormList.get(j)
								.getThirdNumber(), arrayFormList.get(j)
								.getFourthNumber(), arrayFormList.get(j)
								.getFifthNumber(), arrayFormList.get(j)
								.getSixthNumber(), ballCount);
				
				// now compare both tickets
				matchCount = LotteryService.getMatchingNumberCount(
						firstTicketCompare, secondTicketCompare);
				//System.out.println(firstTicketCompare.toString() +" X " +secondTicketCompare.toString()+ "  "+matchCount+"  "+ballCount);
				// that means all balls matched on both tickets! so it is a
				// duplicate ticket
				if (matchCount == ballCount) {

					return FOUND_DUPLICATE_FROM_PREVIOUS_PURCHASE;
				}
			}
		}
		return duplicateStatus;
	}

}
