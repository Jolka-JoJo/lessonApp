package lt.ku.LessonApp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lt.ku.LessonApp.entities.User;
import lt.ku.LessonApp.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "home";
	}
	
	@GetMapping("/new")  
	public String userNew(Model model) {
		model.addAttribute("user", new User());
		return "user/userNew";
	}
	
	@PostMapping("/new")
	public String addUser(
			@Valid
			@ModelAttribute
			User user,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			return "user/userNew";
		}
		userService.addUser(user);
		return "redirect:/";
	}
	
	@GetMapping("/update/{id}")
	public String userNew(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.getUser(id));
		return "user/userUpdate";
	}
	
	@PostMapping("/update/{id}")
	public String userUpdate(
			@PathVariable("id") Integer id, 
			@Valid
			@ModelAttribute User user,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			return "user/userUpdate";
		}
		userService.updateUser(user);
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String userDelete(@PathVariable("id") Integer id) {
		userService.deleteUser(id);
		return "redirect:/";
	}
}
