package com.studentLotto.student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.AssertTrue;

public class TicketList {
	private List<LotteryTicketForm> ticketList = new ArrayList<LotteryTicketForm>();
	private boolean valid;

	public TicketList() {
	}

	public TicketList(List<LotteryTicketForm> ticketList) {
		this.ticketList = ticketList;
	}

	public List<LotteryTicketForm> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<LotteryTicketForm> ticketList) {
		this.ticketList = ticketList;
	}

	@AssertTrue(message = "Can't choose the same number twice for the same ticket")
	private boolean isValid() {
		System.out.println(" LIST ABCDDDDDDDDDDDDDDDDDDDDD");
		Iterator<LotteryTicketForm> it = ticketList.iterator();
		valid = true;
		while (it.hasNext()) {
			LotteryTicketForm form = it.next();
			System.out.println(form.getFirstNumber() + "  "
					+ form.getSecondNumber());
			// ticket is not configured!
			if (form.getFirstNumber() == 0) {
				continue;
			}
			if (form.getFirstNumber() == form.getSecondNumber()) {
				valid = false;
				return false;
			} else if (form.getFirstNumber() == form.getThirdNumber()) {
				valid = false;
				return false;
			} else if (form.getFirstNumber() == form.getFourthNumber()) {
				valid = false;
				return false;
			} else if (form.getFirstNumber() == form.getFifthNumber()) {
				valid = false;
				return false;
			} else if (form.getFirstNumber() == form.getSixthNumber()) {
				valid = false;
				return false;
			}

			if (form.getSecondNumber() == form.getThirdNumber()) {
				valid = false;
				return false;
			} else if (form.getSecondNumber() == form.getFourthNumber()) {
				valid = false;
				return false;
			} else if (form.getSecondNumber() == form.getFifthNumber()) {
				valid = false;
				return false;
			} else if (form.getSecondNumber() == form.getSixthNumber()) {
				valid = false;
				return false;
			}

			else if (form.getThirdNumber() == form.getFourthNumber()) {
				valid = false;
				return false;
			} else if (form.getThirdNumber() == form.getFifthNumber()) {
				valid = false;
				return false;
			} else if (form.getThirdNumber() == form.getSixthNumber()) {
				valid = false;
				return false;
			}

			else if (form.getFourthNumber() == form.getFifthNumber()) {
				valid = false;
				return false;
			} else if (form.getThirdNumber() == form.getSixthNumber()) {
				valid = false;
				return false;
			}

			else if ((form.getFifthNumber() != 0)
					&& (form.getFifthNumber() == form.getSixthNumber())) {
				valid = false;
				return false;
			}

			else {
				valid = true;
			}

		}

		return valid;
	}
}
