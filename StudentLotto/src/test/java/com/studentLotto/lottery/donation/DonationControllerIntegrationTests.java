package com.studentLotto.lottery.donation;
import java.awt.List;
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
import com.studentLotto.university.University;
import com.studentLotto.university.UniversityRepository;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class DonationControllerIntegrationTests  extends WebAppConfigurationAware  {
	@Autowired
	private DonationRepository donateRepositoryMock;

	@Autowired
	private UniversityRepository universityRepositoryMock;
	
	@Autowired
	private AccountRepository accountRepositoryMock;
	
	@Test
	public void getDonationForm() throws Exception{
		mockMvc.perform(get("/donate").session(makeAuthSession()))
		.andExpect(view().name("student/donate"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void submitEmptyForm() throws Exception {
		java.util.List<University> allSchools = new ArrayList<University>();
		allSchools.add(new University());
		mockMvc.perform(
				post("/donate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.session(makeAuthSession())
				.param("amount", "")
				.param("universityId", "")
				)
                .andExpect(view().name("student/donate"))
                .andExpect(model().attributeHasFieldErrors("donationForm", "amount"))
                .andExpect(model().attributeHasFieldErrors("donationForm", "universityId"))
                .andExpect(model().hasErrors());
	}
	
	@Test
	public void submitLowValues() throws Exception {
		java.util.List<University> allSchools = new ArrayList<University>();
		allSchools.add(new University());
		mockMvc.perform(
				post("/donate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.session(makeAuthSession())
				.param("amount", "-1")
				.param("universityId", "-1")
				)
                .andExpect(view().name("student/donate"))
                .andExpect(model().attributeHasFieldErrors("donationForm", "amount"))
                .andExpect(model().attributeHasFieldErrors("donationForm", "universityId"))
                .andExpect(model().hasErrors());
	}
	
    private MockHttpSession makeAuthSession() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        
        Authentication authToken = new UsernamePasswordAuthenticationToken("", "", null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return session;
    }
}
