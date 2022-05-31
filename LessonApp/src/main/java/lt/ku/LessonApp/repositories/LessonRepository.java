package lt.ku.LessonApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.LessonApp.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

}
