package com.studentLotto.lottery.donation;

import java.awt.List;
import java.security.Principal;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.studentLotto.account.AccountRepository;
import com.studentLotto.config.WebAppConfigurationAware;
import com.studentLotto.lottery.LotteryRepository;
import com.studentLotto.lottery.transaction.CreditCardTransactionRepository;
import com.studentLotto.university.University;
import com.studentLotto.university.UniversityRepository;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class TransactionControllerIntegrationTest   extends WebAppConfigurationAware  {
	
	@Autowired
	private DonationRepository donationRepository;
	@Autowired
	private AccountRepository accountRepository;	
	@Autowired
	private LotteryRepository lotteryRepository;	
	@Autowired
	private CreditCardTransactionRepository ccTransactionRepository;
	
	
    private MockHttpSession makeAuthSession() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        
        Authentication authToken = new UsernamePasswordAuthenticationToken("", "", null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return session;
    }
    
	@Test
	public void getPayBillForm() throws Exception{
		Principal principal = createPrincipal();
		
		mockMvc.perform(get("/bill/pay").session(makeAuthSession()).principal(principal))
		.andExpect(view().name("payments/paybill"))
		.andExpect(status().isOk());
	}

	private Principal createPrincipal() {
		Principal principal = new Principal() {
			
			@Override
			public String getName() {
				return "test@test.com";
			}
		};
		return principal;
	}
	
	@Test
	public void submitEmptyForm() throws Exception {
		mockMvc.perform(
				post("/bill/pay")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.session(makeAuthSession())
				.principal(createPrincipal())
				.param("donationIDs", "")
				.param("securityCode", "")
				.param("number", "")
				.param("amount", "")
				.param("firstName", "")
				.param("lastName", "")
				.param("billingStreetAddress", "")
				.param("billingCity", "")
				.param("billingState", "")
				.param("billingZip", "")
				)
                .andExpect(view().name("payments/paybill"))
                .andExpect(model().attributeHasFieldErrors("payBillForm", "number"))
                .andExpect(model().attributeHasFieldErrors("payBillForm", "securityCode"))
                .andExpect(model().attributeHasFieldErrors("payBillForm", "firstName"))
                .andExpect(model().attributeHasFieldErrors("payBillForm", "lastName"))
                .andExpect(model().attributeHasFieldErrors("payBillForm", "billingStreetAddress"))
                .andExpect(model().attributeHasFieldErrors("payBillForm", "billingCity"))
                .andExpect(model().attributeHasFieldErrors("payBillForm", "billingState"))
                .andExpect(model().attributeHasFieldErrors("payBillForm", "billingZip"))
                .andExpect(model().hasErrors());
	}

}
