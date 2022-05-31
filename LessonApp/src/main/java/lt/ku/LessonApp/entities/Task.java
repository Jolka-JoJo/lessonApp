package lt.ku.LessonApp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="task")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column 
	private String question;
	
	@Column
	private String answer;
	
	@Column
	private String filename;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lessonId")
	private Lesson lesson;

	public Task() {
	}

	public Task(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public Task(String question, String answer, String filename) {
		this.question = question;
		this.answer = answer;
		this.filename = filename;
	}

	public Task(String question, String answer, Lesson lesson) {
		this.question = question;
		this.answer = answer;
		this.lesson = lesson;
	}

	public Task(String question, String answer, String filename, Lesson lesson) {
		this.question = question;
		this.answer = answer;
		this.filename = filename;
		this.lesson = lesson;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
