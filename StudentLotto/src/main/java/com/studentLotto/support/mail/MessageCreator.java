package com.studentLotto.support.mail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.HtmlUtils;

import com.studentLotto.student.LotteryTicket;

public class MessageCreator {
	private String root;

	public MessageCreator() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String pathMap = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		String applicationName = request.getRequestURI().replace(pathMap, "");
		this.root = String.format("http://%s:%d%s", serverName, serverPort,
				applicationName);
	}

	public String registrationValidationEmail(String email, String code) {
		String text = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "\n"
				+ "    <title>Student Lotto - Home</title>\n"
				+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
				+ "    <link href=\"${root}/StudentLotto/resources/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <link href=\"${root}/StudentLotto/resources/css/core.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <script src=\"http://code.jquery.com/jquery-latest.js\"></script>\n"
				+ "    <script src=\"${root}/StudentLotto/resources/js/bootstrap.min.js\"></script>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "    <h1>\n"
				+ "        Thank you for registering with Student Lotto\n"
				+ "    </h1>\n"
				+ " \t<p class=\"lead\">\n"
				+ "          We need to verify your email address before your account is activated.\n"
				+ "    </p>\n"
				+ "    <p class=\"lead\">\n"
				+ "    \tTo activate your account please click the following link:\n"
				+ " \t\t\t<a href=\"${root}/activation?id=${code}&email=${email}\">Click to activate!</a>\n"
				+ " \t</p>\n" + "</div>\n" + "</body>\n" + "</html>";
		return text.replace("${root}", root).replace("${email}", URLEncoder.encode(email))
				.replace("${code}", code);
	}

	public String registrationSuccessEmail() {
		String text = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "\n"
				+ "    <title>Student Lotto - Home</title>\n"
				+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
				+ "    <link href=\"${root}/StudentLotto/resources/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <link href=\"${root}/StudentLotto/resources/css/core.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <script src=\"http://code.jquery.com/jquery-latest.js\"></script>\n"
				+ "    <script src=\"${root}/StudentLotto/resources/js/bootstrap.min.js\"></script>\n"
				+ "</head>\n" + "<body>\n" + "    <h1>\n"
				+ "        Thank you for registering with Student Lotto\n"
				+ "    </h1>\n" + " \t<p class=\"lead\">\n"
				+ "         Your registration is now complete.\n"
				+ "    </p>\n" + "</div>\n" + "</body>\n" + "</html>";
		return text;
	}
	
	public String notifyWinner(String amount) {
		String text = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "\n"
				+ "    <title>Student Lotto</title>\n"
				+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
				+ "    <link href=\"${root}/resources/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <link href=\"${root}/resources/css/core.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <script src=\"http://code.jquery.com/jquery-latest.js\"></script>\n"
				+ "    <script src=\"${root}/resources/js/bootstrap.min.js\"></script>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "    <h1>\n"
				+ "        Congratulations! You are amongst the Lottery Winner - Student Lotto\n"
				+ "    </h1>\n"
				+ "<p class=\"lead\">\n"
				+ "   One your lottery ticket had a match with the winning numbers.\n"
				+ "   Please contact the administrator to claim your price of ${amount} dollars.\n"
				+ "</p>\n"
				+ "</body>\n" + "</html>";

		return text.replace("${amount}", amount);
	}

	public String passwordResetEmail(String emailAddress, String guid) {
		String text = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "\n"
				+ "    <title>Student Lotto</title>\n"
				+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
				+ "    <link href=\"${root}/resources/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <link href=\"${root}/resources/css/core.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <script src=\"http://code.jquery.com/jquery-latest.js\"></script>\n"
				+ "    <script src=\"${root}/resources/js/bootstrap.min.js\"></script>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "    <h1>\n"
				+ "        Password Reset - Student Lotto\n"
				+ "    </h1>\n"
				+ "<p class=\"lead\">\n"
				+ "   Someone requested to reset your password on Student Lotto for the account ${email}.\n"
				+ "   If this was not you, please ignore this email. Your account will be safe.\n"
				+ "</p>\n"
				+ "    <p class=\"lead\">\n"
				+ "    \tTo reset your password please click the following link:​\n"
				+ " \t\t\t<a href=\"${root}/passwordReset/change?id=${guid}&email=${email}\">${root}/passwordReset/change?id=${guid}&email=${email}</a>\n"
				+ " \t</p>\n" + "</div>\n" + "</body>\n" + "</html>";

		return text.replace("${root}", root).replace("${email}", emailAddress)
				.replace("${guid}", guid);
	}

	public String purchaseTicketSuccessEmail(long transactionId,
			List<LotteryTicket> tickets, double amountPerTicket) {
		Iterator<LotteryTicket> it = tickets.iterator();
		int count = 0;
		String ticketString = "Ticket #" + (count + 1) + ":  [";
		double total = amountPerTicket * tickets.size();

		while (it.hasNext()) {
			LotteryTicket ticket = it.next();
			ticketString += ticket.getFirstNumber() + ", "
					+ ticket.getSecondNumber() + ", " + ticket.getThirdNumber()
					+ ", " + ticket.getFourthNumber();

			if (ticket.getFifthNumber() != 0) {
				ticketString += ", " + ticket.getFifthNumber();
			}
			if (ticket.getSixthNumber() != 0) {
				ticketString += ", " + ticket.getSixthNumber();
			}
			ticketString += "] <br>";

			count += 1;
			
			if (count <= tickets.size()-1) {
				ticketString += "Ticket #" + (count + 1) + ":  [";
			}
		

		}
		String text = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "\n"
				+ "    <title>Student Lotto</title>\n"
				+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
				+ "    <link href=\"${root}/resources/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <link href=\"${root}/resources/css/core.css\" rel=\"stylesheet\" media=\"screen\" />\n"
				+ "    <script src=\"http://code.jquery.com/jquery-latest.js\"></script>\n"
				+ "    <script src=\"${root}/resources/js/bootstrap.min.js\"></script>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "    <h1>\n"
				+ "       Ticket Purchase Confirmation - Student Lotto\n"
				+ "    </h1>\n"
				+ "<p class=\"lead\">\n"
				+ "   Your payment was succesfully processed. Please save this confirmation numkber for reference: ${transactionId}.\n "

				+ "</p>\n"
				+ "<p class=\"leadd\">\n  ${ticketString} "

				+ "</p>\n"
				+ "<p class=\"leadd\">\n  Each ticket cost $ ${amountPerTicket}. Your total is: $ ${total} "
				+ "</p>\n" + "</div>\n" + "</body>\n" + "</html>";

		return text.replace("${root}", root)
				.replace("${transactionId}", transactionId + "")
				.replace("${ticketString}", ticketString + "")
				.replace("${amountPerTicket}", amountPerTicket + "")
				.replace("${total}", total + "");
	}

}
