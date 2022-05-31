package lt.ku.LessonApp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lt.ku.LessonApp.entities.Task;
import lt.ku.LessonApp.services.FileStorageService;
import lt.ku.LessonApp.services.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FileStorageService fileStorageService;
	
	@GetMapping("/")
	public String taskList(Model model) {
		model.addAttribute("tasks", taskService.getTasks());
		return "task/taskList";
	}
	
	@GetMapping("/new")
	public String taskNew(Model model) {
		model.addAttribute("task", new Task());
		return "task/taskNew";
	}
	
	@PostMapping("/new")
	public String taskNew(
					@RequestParam("question") String question,
					@RequestParam("answer") String answer,
					@RequestParam ("file") MultipartFile file) {

		Task task = new Task(question, answer);
		if(!file.getOriginalFilename().isBlank()) {
			task.setFilename(file.getOriginalFilename());
			fileStorageService.store(file, task.getFilename());
		}
		if(!question.isBlank()|| !answer.isBlank()) {
			taskService.addTask(task);
			return "redirect:/task/";
		}
		return "task/taskNew";
	}
	
	@GetMapping("/delete/{id}")
	public String taskDelete(@PathVariable("id") Integer id) {
		taskService.deleteTask(id);
		return "redirect:/task/";
	}
	
	@GetMapping("/update/{id}")
	public String taskUpdate(
					@PathVariable("id") Integer id, 
					Model model) {
		model.addAttribute("task", taskService.getTask(id));
		return "task/taskUpdate";
	}
	
	@PostMapping("/update/{id}")
	public String taskUpdate(
					@PathVariable("id") Integer id, 
					@ModelAttribute Task task,
					Model model) {
		if(!task.getQuestion().isBlank()|| !task.getAnswer().isBlank()) {
			taskService.updateTask(task);
			return "redirect:/task/";
		}
		model.addAttribute("task", taskService.getTask(id));
		return "task/taskUpdate";
	}
	
	@GetMapping("/files/{id}")
	@ResponseBody
	public ResponseEntity<Resource> getAgreement(@PathVariable Integer id) {
		Task task = taskService.getTask(id);
		Resource file=fileStorageService.loadFile(task.getFilename());
		
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+task.getFilename()+"\"")
				.body(file);
	}
}
