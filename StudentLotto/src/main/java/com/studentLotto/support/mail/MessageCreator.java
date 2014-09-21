package com.studentLotto.support.mail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class MessageCreator {
	private String root;
	
	public MessageCreator(){
		this.root = "http://localhost:8080";
	}
	
	public String registrationValidationEmail(String email){
		String text = "<!DOCTYPE html>\n" +
	            "<html>\n" +
	            "<head>\n" +
	            "\n" +
	            "    <title>Student Lotto - Home</title>\n" +
	            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
	            "    <link href=\"${root}/StudentLotto/resources/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\" />\n" +
	            "    <link href=\"${root}/StudentLotto/resources/css/core.css\" rel=\"stylesheet\" media=\"screen\" />\n" +
	            "    <script src=\"http://code.jquery.com/jquery-latest.js\"></script>\n" +
	            "    <script src=\"${root}/StudentLotto/resources/js/bootstrap.min.js\"></script>\n" +
	            "</head>\n" +
	            "<body>\n" +
	            "    <h1>\n" +
	            "        Thank you for registering with Student Lotto\n" +
	            "    </h1>\n" +
	            " \t<p class=\"lead\">\n" +
	            "          We need to verify your email address before your account is activated.\n" +
	            "    </p>\n" +
	            "    <p class=\"lead\">\n" +
	            "    \tTo activate your account please click the following link:​\n" +
	            " \t\t\t<a href=\"${root}/StudentLotto/activation?id=THISISAUNIQUEGUID\">${root}/StudentLotto/activation?id=THISISAUNIQUEGUID&email=${email}</a>\n" +
	            " \t</p>\n" +
	            "</div>\n" +
	            "</body>\n" +
	            "</html>";
		return text.replace("${root}", root).replace("${email}", email);				
	}
	


}
