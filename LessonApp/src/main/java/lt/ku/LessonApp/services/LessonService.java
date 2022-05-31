package lt.ku.LessonApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.ku.LessonApp.entities.Lesson;
import lt.ku.LessonApp.repositories.LessonRepository;

@Service
public class LessonService {
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	UserService userService;
	
	public Lesson addLesson(Lesson lesson) {
		return lessonRepository.save(lesson);
	}
	
	public List<Lesson> getLessons(){
		return lessonRepository.findAll();
	}
	
	public Lesson updateLesson(Lesson lesson) {
		Lesson old = lessonRepository.getById(lesson.getId());
		old.setTitle(lesson.getTitle());
		old.setUserLessons(lesson.getUserLessons());
		lessonRepository.save(old);
		return old;
	}
	
	public void deleteLesson(Integer id) {
		lessonRepository.deleteById(id);
	}
	
	public Lesson getLesson(Integer id) {
		return lessonRepository.findById(id).get();
	}

}
