package lt.ku.LessonApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.LessonApp.entities.Task;


public interface TaskRepository extends JpaRepository <Task, Integer>  {

}
