package lt.ku.LessonApp.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

@Entity
@Table(name="lesson")
public class Lesson implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String title;
	
	@ManyToMany 
	@JoinTable(
			  name = "userLesson", 
			  inverseJoinColumns = @JoinColumn(name = "user_id"), 
			  joinColumns = @JoinColumn(name = "lesson_id"))
	private List<User> userLessons;
	
	@OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
	private List<Task> tasks;

	public Lesson() {
	}

	public Lesson(String title, List<User> userLessons) {
		this.title = title;
		this.userLessons = userLessons;
	}

	public Lesson(String title) {
		this.title = title;
	}

	public Lesson(String title, List<User> userLessons, List<Task> tasks) {
		this.title = title;
		this.userLessons = userLessons;
		this.tasks = tasks;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<User> getUserLessons() {
		return userLessons;
	}

	public void setUserLessons(List<User> userLessons) {
		this.userLessons = userLessons;
	}

}
