package lt.ku.LessonApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.ku.LessonApp.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
