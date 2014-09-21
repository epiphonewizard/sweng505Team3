package com.studentLotto.signin;

import java.util.Arrays;
import java.util.Locale;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;

import com.studentLotto.support.mail.MailSenderImpl;
import com.studentLotto.support.mail.MessageCreator;

@Controller
public class SigninController {

	@RequestMapping(value = "signin")
	public String signin() {		
        return "signin/signin";
    }

}
