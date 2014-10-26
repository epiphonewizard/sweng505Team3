package com.studentLotto.university;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
public class UniversityController {
	private static final String ADD_UNIVERSITY_VIEW_NAME = "admin/addUniversity";
	private static final String EDIT_UNIVERSITY_VIEW_NAME = "admin/editUniversity";
	private static final String ADD_UNIVERSITY_URL = "addUniversity";
	private static final String UPDATE_UNIVERSITY_URL = "updateUniversity";
	private static final String EDIT_UNIVERSITY_URL = "editUniversity";
	private static final String DELETE_UNIVERSITY_URL = "deleteUniversity";

	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = ADD_UNIVERSITY_URL, method = RequestMethod.GET)
	public String index(Principal principal, Model model, HttpServletRequest request) {
		if (request.getParameter("university") != null){
			String universityName = request.getParameter("university");
			University universityToUpdate = universityRepository.findByName(universityName);
			model.addAttribute(new UniversityForm(universityToUpdate));
			model.addAttribute("actionURL", UPDATE_UNIVERSITY_URL);
		} else {
			model.addAttribute(new UniversityForm());
			model.addAttribute("actionURL", ADD_UNIVERSITY_URL);
		}
		return principal != null ? ADD_UNIVERSITY_VIEW_NAME : "redirect:/signin";
	}

	@RequestMapping(value = ADD_UNIVERSITY_URL, method = RequestMethod.POST)
	public String addUniversity(@Valid @ModelAttribute UniversityForm addUniversityForm,
			Errors errors, RedirectAttributes ra, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("actionURL", ADD_UNIVERSITY_URL);
			return ADD_UNIVERSITY_VIEW_NAME;
		}
		University university = addUniversityForm.createUniversity();
		if (universityRepository.findByName(university.getName()) == null) {
			universityRepository.save(university);
			MessageHelper.addSuccessAttribute(ra, "addUniversity.success", university.getName());
			return "redirect:/addUniversity";
		} else {
			MessageHelper.addErrorAttribute(ra, "addUniversity.failure", university.getName());
			return "redirect:/addUniversity";
		}
	}
	
	@RequestMapping(value = UPDATE_UNIVERSITY_URL, method = RequestMethod.POST)
	public String updateUniversity(@Valid @ModelAttribute UniversityForm addUniversityForm,
			Errors errors, RedirectAttributes ra, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("actionURL", UPDATE_UNIVERSITY_URL);
			return ADD_UNIVERSITY_VIEW_NAME;
		}
		University university = addUniversityForm.createUniversity();	
		if (university.getId() != null) { 
			universityRepository.update(university);
			MessageHelper.addSuccessAttribute(ra, "updateUniversity.success", university.getName());
			return "redirect:/addUniversity?university=" + university.getName();
		} else {
			MessageHelper.addErrorAttribute(ra, "updateUniversity.failure", university.getName());
			return "redirect:/addUniversity?university=" + university.getName();
		}
	}
	
	@RequestMapping(value = EDIT_UNIVERSITY_URL, method = RequestMethod.GET)
	public String index(Principal principal, Model model) {

		// Get university list from repo
		List<University> universityList = universityRepository.getUniversityList();
		University selectedUni = new University();
		
		// set the list as attribute
		model.addAttribute("universityList", universityList);
		
		// set selected university as attribute
		model.addAttribute("selectedUni", selectedUni);

		return principal != null ? EDIT_UNIVERSITY_VIEW_NAME
				: "redirect:/signin";
	}

	@RequestMapping(value = EDIT_UNIVERSITY_URL, method = RequestMethod.POST)
	public String editUniversity(
			Model model,
			@ModelAttribute University selectedUni, 
			Errors errors,
			RedirectAttributes ra) {
		// if no errors were found
		if (errors.hasErrors()) {
			return EDIT_UNIVERSITY_VIEW_NAME;
		}
		return "redirect:/addUniversity?university=" + selectedUni.getName();
	}
	
	@RequestMapping(value = DELETE_UNIVERSITY_URL, method = RequestMethod.POST)
	public String deleteUniversity(@Valid @ModelAttribute UniversityForm addUniversityForm,
			Errors errors, RedirectAttributes ra, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("actionURL", UPDATE_UNIVERSITY_URL);
			return ADD_UNIVERSITY_VIEW_NAME;
		}
		University university = universityRepository.findById(addUniversityForm.getId());
		if (university.getId() != null && university.getLotteries() == null) {
			universityRepository.remove(university);
			MessageHelper.addSuccessAttribute(ra, "deleteUniversity.success", university.getName());
			return "redirect:/addUniversity";
		} else {
			MessageHelper.addErrorAttribute(ra, "deleteUniversity.failure", university.getName());
			return "redirect:/addUniversity";
		}
	}
}
