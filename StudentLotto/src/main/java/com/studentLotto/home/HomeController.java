package com.studentLotto.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.studentLotto.support.web.MessageHelper;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal) {
		return principal != null ? "home/homeSignedIn" : "redirect:/signin";
	}
	
	@RequestMapping(value="activation", method = RequestMethod.GET)
	public String activation(RedirectAttributes ra) {
        MessageHelper.addSuccessAttribute(ra, "email.activate");
        return "redirect:/signin";				
	}
	
}
