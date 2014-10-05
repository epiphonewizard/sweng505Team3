package com.studentLotto.university;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.UserService;
import com.studentLotto.support.web.MessageHelper;

@Controller
public class AddUniversityController {
	private static final String ADD_UNIVERSITY_VIEW_NAME = "admin/addUniversity";

	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "addUniversity")
	public String addUniversity(Model model) {
		model.addAttribute(new AddUniversityForm());
		return ADD_UNIVERSITY_VIEW_NAME;
	}

	@RequestMapping(value = "addUniversity", method = RequestMethod.POST)
	public String addUniversity(@Valid @ModelAttribute AddUniversityForm addUniversityForm,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return ADD_UNIVERSITY_VIEW_NAME;
		}
		University university = universityRepository.save(addUniversityForm.createUniversity());
		MessageHelper.addSuccessAttribute(ra, "addUniversity.success");
		return "redirect:/addUniversity";
	}

	/**
	 * reference data used in creation of page. This will need to be updated
	 * for real values once database is completely set up.
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
