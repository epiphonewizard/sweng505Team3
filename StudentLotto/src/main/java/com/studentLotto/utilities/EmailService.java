package com.studentLotto.utilities;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author Elie
 *
 */
public class EmailService {
	Properties props;

	/**
	 * Configure the basic email service per google SMTP info
	 */
	public EmailService() {
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

	}

	/**
	 * Method used to send email
	 * 
	 * @param recipientEmail
	 *            the recipient email
	 * @param title
	 *            the email title
	 * @param emailMessage
	 *            the email body or message
	 * @throws AddressException
	 *             thrown the email address is incorrect
	 * @throws MessagingException
	 *             thrown if there is a messaging exception
	 */
	public void sendEmail(String recipientEmail, String title,
			String emailMessage) throws AddressException, MessagingException {

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"Sweng505team3@gmail.com", "pennstate14");
					}
				});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("Sweng505team3@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(recipientEmail));
		message.setSubject(title);
		message.setText(emailMessage);
		Transport.send(message);

	}

}
