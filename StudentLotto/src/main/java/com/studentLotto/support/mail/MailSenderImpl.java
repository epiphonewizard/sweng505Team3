package com.studentLotto.support.mail;


import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * This class is responsible for sending emails. That is it.
 *
 */
public class MailSenderImpl {
	private MailSender mailSender;
	
	public MailSenderImpl(){
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		
		senderImpl.setHost("smtp.gmail.com");
		senderImpl.setPort(587);
		senderImpl.setUsername("Sweng505team3@gmail.com");
		senderImpl.setPassword("PennState!2");
		Properties mailProps = new Properties();
		mailProps.setProperty("mail.smtp.auth", "true");
		mailProps.setProperty("mail.smtp.starttls.enable", "true");
		senderImpl.setJavaMailProperties(mailProps);
		
		this.mailSender = senderImpl;
	}
	 
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
 
	public void sendMail(String from, String to, String subject, String html) {
		try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "false");
            props.put("mail.smtp.ssl.enable", "true");

            Session session = Session.getInstance(props, new EmailAuth());
            Message msg = new MimeMessage(session);

            InternetAddress fromAddress = new InternetAddress(from, "Student Lotto");
            msg.setFrom(fromAddress);

            InternetAddress toAddress = new InternetAddress(to);

            msg.setRecipient(Message.RecipientType.TO, toAddress);

            msg.setSubject(subject);
            msg.setContent(html, "text/html");
            Transport.send(msg);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    static class EmailAuth extends Authenticator {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication("sweng505team3@gmail.com", "PennState!2");

        }
    }
}
