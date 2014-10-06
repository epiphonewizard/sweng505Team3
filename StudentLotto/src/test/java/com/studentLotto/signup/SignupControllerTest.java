package com.studentLotto.signup;

import java.util.Date;

import org.junit.*;
import org.mockito.Mock;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountRepository;
import com.studentLotto.config.WebAppConfigurationAware;

public class SignupControllerTest extends WebAppConfigurationAware {
	
	private static SignupForm signupForm = new SignupForm();
	
	@Mock
	private static AccountRepository accountRepositoryMock;
	
	@BeforeClass
	public static void setup() {
		signupForm.setDateOfBirth(new Date());
		signupForm.setEmail("test@test.com");
		signupForm.setFirstName("John");
		signupForm.setHomeCity("Hometown");
		signupForm.setHomeCountry("USA");
		signupForm.setHomeState("MO");
		signupForm.setHomeStreetAddress("123 Fake St.");
		signupForm.setHomeZip("66666");
		signupForm.setLastName("Doe");
		signupForm.setMailCity("Hometown");
		signupForm.setMailCountry("USA");
		signupForm.setMailState("MO");
		signupForm.setMailStreetAddress("123 Fake St.");
		signupForm.setMailZip("66666");
		signupForm.setMajor("Physics");
		signupForm.setPassword("test");
		signupForm.setPhoneNumber("555-555-5555");
		signupForm.setSchool(new Long(1));
		signupForm.setUserType("Student");
		
	}
	
	@AfterClass
	public static void teardown() {
		Account account = accountRepositoryMock.findByEmail(signupForm.getEmail());
		accountRepositoryMock.remove(account);    	
	}
	
    /*@Test
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
    }*/
    
    @Test
    public void createStudent() throws Exception {
    	mockMvc.perform(post("/signup").requestAttr("signupForm", signupForm));
    	Account account = accountRepositoryMock.findByEmail(signupForm.getEmail());
    	assertTrue(account!=null);    	
    }
    
}