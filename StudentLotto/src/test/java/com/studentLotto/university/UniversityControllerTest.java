package com.studentLotto.university;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.studentLotto.config.WebAppConfigurationAware;

public class UniversityControllerTest extends WebAppConfigurationAware{

	@Mock
	private UniversityRepository universityRepositoryMock;
	
	@Test
	public void submitEmptyUniversityForm() throws Exception { 
		mockMvc.perform(post("/addUniversity")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("universityForm", new UniversityForm()).session(makeAuthSession())
			)
				.andExpect(status().isOk())
				.andExpect(view().name("admin/manageUniversity"))
		        .andExpect(model().attributeHasFieldErrors("universityForm", "name"))
				.andExpect(model().attributeHasFieldErrors("universityForm", "addressLine1"))
				.andExpect(model().attributeHasFieldErrors("universityForm", "addressCity"))
				.andExpect(model().attributeHasFieldErrors("universityForm", "addressState"))
				.andExpect(model().attributeHasFieldErrors("universityForm", "addressZip"));		
	}
	
	private MockHttpSession makeAuthSession() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        Authentication authToken = new UsernamePasswordAuthenticationToken("", "", null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return session;
    }
}
