package com.bootstrap.controllers;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootstrap.dao.model.RegistrationForm;
import com.bootstrap.dao.model.Subscriber;
import com.bootstrap.dao.repositories.jpa.UserRepository;
import com.bootstrap.dao.services.UserValidator;

@Controller
@RequestMapping
public class RegistrationController {

	private UserValidator validator;
	private UserRepository userRepo;
	private PasswordEncoder encoder;

	public RegistrationController(UserRepository userRepo, PasswordEncoder encoder, UserValidator validator) {
		this.userRepo = userRepo;
		this.encoder = encoder;
		this.validator = validator;
	}

	@GetMapping("/admin/register")
	public String registerForm(Model model) {
		model.addAttribute("subscriber", new Subscriber());
		model.addAttribute("data", new RegistrationForm());
		return "registration";
	}

	@PostMapping("/admin/register")
	public String processRegistration(Model model, @Valid @ModelAttribute("data") RegistrationForm formData,
			Errors errors) {
		validator.validate(formData, errors);
		if (errors.hasErrors()) {
			model.addAttribute("form", new RegistrationForm());
			model.addAttribute("subscriber", new Subscriber());
			return "registration";
		}
		userRepo.save(formData.toUser(encoder));
		return "redirect:/login";
	}

	@GetMapping("/admin/login")
	public String getLoginPage(Model model) {
		model.addAttribute("subscriber", new Subscriber());
		return "login";
	}

}