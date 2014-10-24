package com.studentLotto.university;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.Account;
import com.studentLotto.account.AccountEditForm;
import com.studentLotto.account.Person;
import com.studentLotto.account.Student;
import com.studentLotto.account.UserService;
import com.studentLotto.support.web.MessageHelper;

@Controller
public class EditUniversityController {
	private static final String EDIT_UNIVERSITY_VIEW_NAME = "admin/editUniversity";
	private static final String EDIT_SELECTED_UNIVERSITY_VIEW_NAME = "admin/editSelectedUniversity";
	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "editUniversity", method = RequestMethod.GET)
	public String index(Principal principal, Model model,
			@ModelAttribute LinkedList<University> universityList,
			@ModelAttribute University selectedUni) {

		// Get university list from repo
		universityList.addAll((List<University>) universityRepository
				.getUniversityList());
		// set the list as attribute
		model.addAttribute("universityList", universityList);
		// set selected university as attribute
		model.addAttribute("selectedUni", selectedUni);

		return principal != null ? EDIT_UNIVERSITY_VIEW_NAME
				: "redirect:/signin";
	}

	@RequestMapping(value = "editUniversity", method = RequestMethod.POST)
	public String edityUniversity(
			@ModelAttribute LinkedList<University> universityList,
			@ModelAttribute University selectedUni, Errors errors,
			RedirectAttributes ra) {
		// if no errors were found
		if (errors.hasErrors()) {
			return EDIT_UNIVERSITY_VIEW_NAME;
		}
		// set message to edit university success
		MessageHelper.addSuccessAttribute(ra, "editUniversity.success");
		return "redirect:/editSelectedUniversity?university="
				+ selectedUni.getName();
	}

	@RequestMapping(value = "editSelectedUniversity", method = RequestMethod.GET)
	public String editSelectedUniversityIndex(Principal principal, Model model,
			HttpServletRequest request) {
		Assert.notNull(principal);
		// find out which university was selected
		String universityName = request.getParameter("university");
		// grab that university info from the database
		University university = universityRepository.findByName(universityName);
		// insert placeholders for the needed info
		model.addAttribute("university", university);
		AddUniversityForm universityForm = new AddUniversityForm(university);
		model.addAttribute(universityForm);

		return principal != null ? EDIT_SELECTED_UNIVERSITY_VIEW_NAME
				: "redirect:/editSelectedUniversity";
	}

	@RequestMapping(value = "editSelectedUniversity", method = RequestMethod.POST)
	public String editSelectedUniversity(Principal principal,
			@Valid @ModelAttribute AddUniversityForm universityForm,
			Errors errors, RedirectAttributes ra) {

		Assert.notNull(principal);
		if (errors.hasErrors()) {
			return EDIT_SELECTED_UNIVERSITY_VIEW_NAME;
		}
		// Create a university from form
		University university = universityForm.createUniversity();

		// update the university in the database
		if (university != null) {

			universityRepository.update(university);
		}
		// display success message
		MessageHelper.addSuccessAttribute(ra, "editUniversity.success");

	//	return "redirect:/editSelectedUniversity?university="
	//	+ university.getName();
		return "redirect:/";
	}

	/**
	 * reference data used in creation of page. This will need to be updated for
	 * real values once database is completely set up.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap();

		Map<String, String> countries = new LinkedHashMap<String, String>();
		countries.put("US", "United Stated");
		countries.put("CHINA", "China");
		countries.put("SG", "Singapore");
		countries.put("MY", "Malaysia");
		referenceData.put("countryList", countries);

		Map<String, String> states = new LinkedHashMap<String, String>();
		states.put("OR", "Oregon");
		states.put("CA", "California");
		states.put("PA", "Pennyslvannia");
		referenceData.put("stateList", states);

		Map<String, String> schools = new LinkedHashMap<String, String>();
		schools.put("PennState", "Penn State");
		schools.put("OregonState", "Oregon State");
		schools.put("USC", "University of Southern California");
		referenceData.put("schoolList", schools);

		return referenceData;
	}

}

