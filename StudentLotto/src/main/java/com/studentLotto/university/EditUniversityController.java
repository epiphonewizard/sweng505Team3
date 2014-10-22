package com.studentLotto.university;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentLotto.account.UserService;
import com.studentLotto.support.web.MessageHelper;

@Controller
@Secured("ROLE_ADMIN")
public class EditUniversityController {
	private static final String EDIT_UNIVERSITY_VIEW_NAME = "admin/editUniversity";

	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "editUniversity", method = RequestMethod.GET)
	public String onLoad(Principal principal, Model model) {
		model.addAttribute("universitylist", universityRepository.findAllUniversities());
		return principal != null ? EDIT_UNIVERSITY_VIEW_NAME : "redirect:/signin";
	}

	/**@RequestMapping(value = "addUniversity", method = RequestMethod.POST)
	public String editUniversity(@Valid @ModelAttribute AddUniversityForm addUniversityForm,
			Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return EDIT_UNIVERSITY_VIEW_NAME;
		}
		if (universityRepository.findByName(addUniversityForm.getName()) == null)  {
			University university = universityRepository.save(addUniversityForm.createUniversity());
			MessageHelper.addSuccessAttribute(ra, "addUniversity.success", addUniversityForm.getName());
			return "redirect:/addUniversity";
		} else {
			addUniversityForm.createUniversity();
			MessageHelper.addErrorAttribute(ra, "addUniversity.failure", addUniversityForm.getName());
			return "redirect:/addUniversity";
		}
	}**/
}
