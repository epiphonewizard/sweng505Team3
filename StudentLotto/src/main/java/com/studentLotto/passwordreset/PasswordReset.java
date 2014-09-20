package com.studentLotto.passwordreset;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.*;
import com.studentLotto.support.web.*;

import org.springframework.stereotype.Controller;

@Controller
public class PasswordReset {

    private static final String PASSWORD_RESET_VIEW_NAME = "password/passwordReset";
	
	@RequestMapping(value = "passwordReset")
	public String passwordReset() {
        return PASSWORD_RESET_VIEW_NAME;
	}


}
