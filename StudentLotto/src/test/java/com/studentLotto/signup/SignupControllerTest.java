package com.studentLotto.signup;

import java.util.Date; 

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.studentLotto.account.Account;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.config.WebAppConfigurationAware;

public class SignupControllerTest extends WebAppConfigurationAware {
		
	@Autowired
	private AccountRepository accountRepositoryMock;
	    
    @Test
    public void submitEmptyForm() throws Exception {    	
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .sessionAttr("signupForm", new SignupForm()).session(makeAuthSession())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("signup/signup"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "firstName"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "lastName"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "email"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "password"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "confirm"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "dateOfBirth"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "phoneNumber"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "homeStreetAddress"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "homeCity"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "homeState"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "homeZip"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "mailStreetAddress"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "mailCity"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "mailZip"));
    }
    
    @Test
    public void createStudent() throws Exception {
		mockMvc.perform(post("/signup")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("dateOfBirth", "4/4/2013")
	                .param("email", "test@test.com")
	                .param("firstName", "John")
	                .param("homeCity", "Hometown")
	                .param("homeState", "MO")
	                .param("homeStreetAddress", "123 Fake St.")
	                .param("homeZip", "66666")
	                .param("lastName", "Doe")
	                .param("mailCity", "Hometown")
	                .param("mailState", "MO")
	                .param("mailStreetAddress", "123 Fake St.")
	                .param("mailZip", "66666")
	                .param("password", "test")
	                .param("confirm", "test")
	                .param("phoneNumber", "5555555555")
	                .param("school", "1")
	                .param("userType", "Student")
	                .sessionAttr("signupForm", new SignupForm()).session(makeAuthSession())
	        ).andExpect(status().isOk());
    	Account account = accountRepositoryMock.findByEmail("test@test.com");
    	assertTrue(account!=null);    	
    }
    
    @Test
    public void createAccountWithExistingEmail() throws Exception {
		mockMvc.perform(post("/signup")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("dateOfBirth", "4/4/2013")
	                .param("email", "test@test.com")
	                .param("firstName", "John")
	                .param("homeCity", "Hometown")
	                .param("homeState", "MO")
	                .param("homeStreetAddress", "123 Fake St.")
	                .param("homeZip", "66666")
	                .param("lastName", "Doe")
	                .param("mailCity", "Hometown")
	                .param("mailState", "MO")
	                .param("mailStreetAddress", "123 Fake St.")
	                .param("mailZip", "66666")
	                .param("password", "test")
	                .param("confirm", "test")
	                .param("phoneNumber", "5555555555")
	                .param("school", "1")
	                .param("userType", "Student")
	                .sessionAttr("signupForm", new SignupForm()).session(makeAuthSession())
	        ).andExpect(status().isOk())
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