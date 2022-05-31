package lt.ku.LessonApp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lt.ku.LessonApp.entities.Lesson;
import lt.ku.LessonApp.entities.Task;
import lt.ku.LessonApp.entities.User;
import lt.ku.LessonApp.services.LessonService;
import lt.ku.LessonApp.services.TaskService;
import lt.ku.LessonApp.services.UserService;

@Controller
@RequestMapping("/lesson")
public class LessonController {
	
	@Autowired
	LessonService lessonService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TaskService taskService;
	
	@GetMapping("/")
	public String lessonList(Model model) {
		model.addAttribute("lessons", lessonService.getLessons());
		return "lesson/lessonList";
	}
	
	@GetMapping("/new")
	public String lessonNew(Model model) {
		model.addAttribute("tasks", taskService.getTasks());
		model.addAttribute("users", userService.getUsers());
		return "lesson/lessonNew";
	}
	
	@PostMapping("/new")
	public String lessonNew(
			Model model,
			@RequestParam(name="taskId", required=false) List<Integer> taskIds,
			@RequestParam(name="userId", required=false) List<Integer> userIds,
			@RequestParam("title") String title){
		
		if(title.isBlank()) {
			model.addAttribute("tasks", taskService.getTasks());
			model.addAttribute("users", userService.getUsers());
			return "lesson/lessonNew";
		}
		Lesson lesson = new Lesson(title);

		if(userIds != null) {
			List<User> users = new ArrayList<User>();
			for (Integer userId : userIds) {
				users.add(userService.getUser(userId));
			}
			lesson.setUserLessons(users);
		}
		
		if(taskIds != null) {
			Task task = new Task();
			for (Integer taskId : taskIds) {
				task = taskService.getTask(taskId);
				task.setLesson(lesson);
			}
		}
		
		lessonService.addLesson(lesson);
		
		return "redirect:/lesson/";
	}
	
	@GetMapping("/delete/{id}")
	public String lessonDelete(@PathVariable("id") Integer id) {
		
		Lesson lesson = lessonService.getLesson(id);
		for (Task task : lesson.getTasks()) {
			task.setLesson(null);
		}
		lessonService.deleteLesson(id);
		
		return "redirect:/lesson/";
	}
	
	@GetMapping("/update/{id}")
	public String lessonUpdate(
			@PathVariable("id") Integer id, 
			Model model) {
		model.addAttribute("lesson", lessonService.getLesson(id));
		model.addAttribute("tasks", taskService.getTasks());
		model.addAttribute("users", userService.getUsers());
		return "lesson/lessonUpdate";
	}
	
	@PostMapping("/update/{id}")
	public String lessonUpdate(
			@PathVariable("id") Integer id,
			@RequestParam(name="taskId", required=false) List<Integer> taskIds,
			@RequestParam(name="userId", required=false) List<Integer> userIds,
			@ModelAttribute Lesson lesson,
			Model model) {
		
		if(lesson.getTitle().isBlank()) {
			model.addAttribute("lesson", lessonService.getLesson(id));
			model.addAttribute("tasks", taskService.getTasks());
			model.addAttribute("users", userService.getUsers());
			return "lesson/lessonUpdate";
		}
		
		if(userIds != null) {
			List<User> users = new ArrayList<User>();
			for (Integer userId : userIds) {
				users.add(userService.getUser(userId));
			}
			lesson.setUserLessons(users);
		}

		Task task = new Task();
		if(taskIds != null) {
			for (Integer taskId : taskIds) {
				task = taskService.getTask(taskId);
				task.setLesson(lesson);
			}
		}
		
		lessonService.updateLesson(lesson);
		return "redirect:/lesson/";
	}
	
	
}
