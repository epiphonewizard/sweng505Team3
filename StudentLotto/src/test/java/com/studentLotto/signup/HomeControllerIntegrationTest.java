package com.studentLotto.signup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.RequestBuilder;

import com.studentLotto.account.AccountRepository;
import com.studentLotto.config.WebAppConfigurationAware;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.lottery.donation.DonationRepository;
import com.studentLotto.student.PurchaseTicketRepo;
import com.studentLotto.utilities.AccountActivationRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.*;

public class HomeControllerIntegrationTest extends WebAppConfigurationAware {
	@Autowired
	private AccountActivationRepository accountActivationRepo;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private DonationRepository donationRepository;
	@Autowired
	private LotteryRepository lotteryRepository;
	@Autowired
	private PurchaseTicketRepo purchaseTicketRepo;
	
	
    @Test
    public void notSignedInDisplaySignIn() throws Exception {    	
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/signin"));
    }
    
    @Test
    public void signedInDisplayHomePage() throws Exception {
        mockMvc.perform(get("/").session(makeAuthSession()).principal(createPrincipal("test@test.com")))
        	.andExpect(status().isOk())
        	.andExpect(view().name("home/homeSignedIn"));
    } 
    
    @Test
    public void studentShouldDisplayStudentInfo() throws Exception{
        mockMvc.perform(get("/").session(makeAuthSession()).principal(createPrincipal("test@test.com")))
    	.andExpect(status().isOk())
    	.andExpect(content().string(containsString("<div id=\"University\"")));    	
    }
    
    @Test
    public void donatorShouldNotDisplayStudentInfo() throws Exception{
        mockMvc.perform(get("/").session(makeAuthSession()).principal(createPrincipal("ryanhicke@gmail.com")))
    	.andExpect(status().isOk())
    	.andExpect(content().string( not(containsString("<div id=\"University\""))));      	
    }
    

	private MockHttpSession makeAuthSession() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        Authentication authToken = new UsernamePasswordAuthenticationToken("", "", null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return session;
    }
	
	private Principal createPrincipal(final String name) {
		Principal principal = new Principal() {
			
			@Override
			public String getName() {
				return name;
			}
		};
		return principal;
	}
}
