package com.studentLotto.signup;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.studentLotto.config.WebAppConfigurationAware;

public class SignupControllerTest extends WebAppConfigurationAware {
	
	private SignupForm signupForm = new SignupForm();
	
	
    @Test
    public void displaysSignupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(model().attributeExists("signupForm"))
                .andExpect(view().name("signup/signup"))
                .andExpect(content().string(
                        allOf(
                                containsString("<title>Signup</title>"),
                                containsString("<legend>Please Sign Up</legend>")
                        ))
                );
    }
    
    
}