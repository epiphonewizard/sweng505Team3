package com.studentLotto.lottery;

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

import com.studentLotto.config.WebAppConfigurationAware;
import com.studentLotto.university.University;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


public class LotteryControllerIntegrationTest extends WebAppConfigurationAware  {
	
	@Autowired
	private LotteryRepository lotteryRepositoryMock;

	@Test
	public void submitEmptyForm() throws Exception {
		java.util.List<University> allSchools = new ArrayList<University>();
		allSchools.add(new University());
		ResultActions actions = mockMvc.perform(
				post("/lottery/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.session(makeAuthSession())
				.param("purchaseStartDate", "")
				.param("purchaseEndDate", "")
				.param("drawingDate", "")
				.param("maxWinnings", "")
				.param("universityID", "1")
				)
                .andExpect(view().name("admin/createLottery"))
                .andExpect(model().attributeHasFieldErrors("createLotteryForm", "purchaseStartDate"))
                .andExpect(model().attributeHasFieldErrors("createLotteryForm", "purchaseEndDate"))
                .andExpect(model().attributeHasFieldErrors("createLotteryForm", "drawingDate"));
	}
	
	@Test
	public void purchaseStartDateAfterEndDate() throws Exception {
		java.util.List<University> allSchools = new ArrayList<University>();
		allSchools.add(new University());
		ResultActions actions = mockMvc.perform(
				post("/lottery/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.session(makeAuthSession())
				.param("purchaseStartDate", "10/16/1985")
				.param("purchaseEndDate", "10/01/1984")
				.param("drawingDate", "")
				.param("maxWinnings", "")
				.param("universityID", "1")
				)
                .andExpect(view().name("admin/createLottery"))
    	        .andExpect(model().hasErrors());
	}
	
	@Test
	public void drawingDateBeforeEndDate() throws Exception {
		java.util.List<University> allSchools = new ArrayList<University>();
		allSchools.add(new University());
		ResultActions actions = mockMvc.perform(
				post("/lottery/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.session(makeAuthSession())
				.param("purchaseStartDate", "10/16/1985")
				.param("purchaseEndDate", "10/18/1985")
				.param("drawingDate", "10/18/1984")
				.param("maxWinnings", "")
				.param("universityID", "1")
				)
                .andExpect(view().name("admin/createLottery"))
    	        .andExpect(model().hasErrors());
	}
	
	@Test
	public void getCreateLottery() throws Exception {
		mockMvc.perform(get("/lottery/create").session(makeAuthSession()))
		.andExpect(view().name("admin/createLottery"))
		.andExpect(status().isOk());
	}
	
    private MockHttpSession makeAuthSession() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        
        Authentication authToken = new UsernamePasswordAuthenticationToken("", "", null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return session;
    }
}
