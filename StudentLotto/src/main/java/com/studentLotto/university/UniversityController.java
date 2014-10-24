package com.studentLotto.university;
import java.security.Principal;
import java.util.LinkedList;
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

	@Autowired
	private UniversityRepository universityRepository;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = ADD_UNIVERSITY_URL, method = RequestMethod.GET)
	public String index(Principal principal, Model model, HttpServletRequest request) {
		if (request.getParameter("university") != null){
			String universityName = request.getParameter("university");
			University university = universityRepository.findByName(universityName);
			model.addAttribute(new UniversityForm(university));
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
		if (university.getName() != null) { 
			universityRepository.update(university);
			model.addAttribute(new UniversityForm(university));
			MessageHelper.addSuccessAttribute(ra, "updateUniversity.success", university.getName());
			return "redirect:/addUniversity?university=" + university.getName();
		} else {
			MessageHelper.addErrorAttribute(ra, "updateUniversity.failure", university.getName());
			return "redirect:/addUniversity?university=" + university.getName();
		}
	}
	
	@RequestMapping(value = "editUniversity", method = RequestMethod.GET)
	public String index(Principal principal, Model model,
			@ModelAttribute LinkedList<University> universityList,
			@ModelAttribute University selectedUni) {

		// Get university list from repo
		universityList.addAll((List<University>) universityRepository.getUniversityList());
		
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
		return "redirect:/addUniversity?university="
				+ selectedUni.getName();
	}
}
