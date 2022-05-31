package lt.ku.LessonApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.ku.LessonApp.entities.Task;
import lt.ku.LessonApp.repositories.TaskRepository;

@Service
public class TaskService {
	
	@Autowired 
	TaskRepository taskRepository;
	
	public List<Task> getTasks(){
		return taskRepository.findAll();
	}
	
	public Task addTask(Task task) {
		return taskRepository.save(task);
	}
	
	public Task updateTask(Task task) {
		Task old = taskRepository.getById(task.getId());
		old.setQuestion(task.getQuestion());
		old.setAnswer(task.getAnswer());
		taskRepository.save(old);
		return old;
	}
	
	public void deleteTask(Integer id) {
		taskRepository.deleteById(id);
	}
	
	public Task getTask(Integer id) {
		return taskRepository.findById(id).get();
	}
}
