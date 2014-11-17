package com.studentLotto.lottery.transaction;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountEditForm;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.lottery.Lottery;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.lottery.donation.DonationForm;
import com.studentLotto.lottery.donation.DonationRepository;
import com.studentLotto.lottery.donation.Donation;
import com.studentLotto.student.LotteryTicket;
import com.studentLotto.student.PurchaseTicketRepo;
import com.studentLotto.support.mail.MailSenderImpl;
import com.studentLotto.support.mail.MessageCreator;
import com.studentLotto.support.web.MessageHelper;
import com.studentLotto.university.University;

@Controller
public class TransactionController {
	@Autowired
	private DonationRepository donationRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private LotteryRepository lotteryRepository;

	@Autowired
	private CreditCardTransactionRepository ccTransactionRepository;

	@Autowired
	private PurchaseTicketRepo ticketRepo;
	private double total;
	private static boolean isPayTicket;

	@RequestMapping(value = "bill/pay", method = RequestMethod.GET)
	public String payBill(Principal principal, Model model) {
		Assert.notNull(principal);
		isPayTicket = false;
		Account account = accountRepository.findByEmail(principal.getName());

		List<Donation> donations = donationRepository
				.findUnpaidForAccount(account.getId());

		model.addAttribute("donations", donations);
		PayBillForm payBillForm = new PayBillForm();
		payBillForm.setDonationIDs(Donation.getDonationIDs(donations));
		payBillForm.setAmount(Donation.getTotal(donations));
		model.addAttribute("payBillForm", payBillForm);

		model.addAttribute("person", account.getPerson());
		return "payments/paybill";
	}

	@RequestMapping(value = "bill/pay", method = RequestMethod.POST)
	public String payBill(Principal principal,
			@Valid @ModelAttribute PayBillForm payBillForm, Errors errors,
			RedirectAttributes ra, Model model) {
		if (isPayTicket) {
			System.out.println("M--2");
			Assert.notNull(principal);
			String[] idList = payBillForm.getDonationIDs().split(",");
			List<LotteryTicket> tickets = new ArrayList<LotteryTicket>();
			for (String id : idList) {
				if (!StringUtils.isEmptyOrWhitespace(id)) {
					tickets.add(ticketRepo.findById(Long.valueOf(id)));
				}
			}
			if (errors.hasErrors()) {
				model.addAttribute("donations", tickets);
				model.addAttribute("donationIDs",
						LotteryTicket.getTicketIDs(tickets));
				model.addAttribute("payBillForm", payBillForm);
				model.addAttribute("totalBill", total);
				return "redirect:/bill/payTicket";
			}

			Account account = accountRepository
					.findByEmail(principal.getName());
			CreditCardTransaction transaction = ccTransactionRepository
					.saveAndFlush(payBillForm.createCCTransaction(account));

			for (LotteryTicket ticket : tickets) {
				ticket.setPaymentComplete(1);
				ticket.setCcTransactionId(transaction.getId());
				ticketRepo.update(ticket);
			}
			MessageHelper.addSuccessAttribute(ra, "payment.successful");
			new MailSenderImpl().sendMail("sweng505team3@gmail.com", account
					.getPerson().getStudent().getUEmailAddress(),
					"Payment Confirmation", new MessageCreator()
							.purchaseTicketSuccessEmail(transaction.getId(),
									tickets, tickets.get(0).getAmount()));

			return "redirect:/";
		} else {
			System.out.println("A--2");
			Assert.notNull(principal);
			String[] idList = payBillForm.getDonationIDs().split(",");
			List<Donation> donations = new ArrayList<Donation>();
			for (String id : idList) {
				if (!StringUtils.isEmptyOrWhitespace(id)) {
					Donation donation = donationRepository.findById(Long
							.valueOf(id));
					if (donation != null) {
						donations.add(donation);

					}

				}
			}
			if (errors.hasErrors()) {
				model.addAttribute("donations", donations);
				model.addAttribute("donationIDs",
						Donation.getDonationIDs(donations));
				model.addAttribute("payBillForm", payBillForm);
				model.addAttribute("totalBill", Donation.getTotal(donations));
				return "payments/paybill";
			}
			Account account = accountRepository
					.findByEmail(principal.getName());
			CreditCardTransaction transaction = ccTransactionRepository
					.saveAndFlush(payBillForm.createCCTransaction(account));

			for (Donation donation : donations) {
				donation.setPaymentComplete(true);
				donation.setCreditCardTransaction(transaction);
				donationRepository.update(donation);
			}

			MessageHelper.addSuccessAttribute(ra, "payment.successful");
		}

		return "redirect:/";
	}

	@RequestMapping(value = "bill/payTicket", method = RequestMethod.GET)
	public String payTicket(Principal principal, Model model) {
		Assert.notNull(principal);

		// set isPayTicket to true in order to process ticket payment instead of
		// donation
		isPayTicket = true;
		// get the user accound
		Account account = accountRepository.findByEmail(principal.getName());
		// get the upcoming lottery for the user account
		Lottery studentLottery = lotteryRepository
				.findUpcomingForUniversity(account.getPerson().getStudent()
						.getUniversity().getId());
		// get the tickets that were not paid
		List<LotteryTicket> tickets = ticketRepo
				.findStudentUnpaidTicketForUpcomingLottery(account.getPerson()
						.getStudent().getId(), studentLottery.getId());
		// set the model attributes to tickets
		model.addAttribute("donations", tickets);
		PayBillForm payBillForm = new PayBillForm();
		// set the ids for the tickets
		payBillForm.setDonationIDs(LotteryTicket.getTicketIDs(tickets));
		payBillForm.setAmount(LotteryTicket.getTotal(tickets));
		model.addAttribute("payBillForm", payBillForm);

		return "payments/paybill";
	}

}
