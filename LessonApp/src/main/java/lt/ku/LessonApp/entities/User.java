package lt.ku.LessonApp.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="user")
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false,length = 64)
	@Length(min=3, max=64, message = "Vardas turi būti ne ilgesnis nei 3 simboliai ir trumpesnis už 64 simbolius")
	@NotBlank (message = "Vardas privalomas")
	private String name;
	
	@Column(nullable = false,length = 64)
	@Length(min=3, max=64, message = "Vardas turi būti ne ilgesnis nei 3 simboliai ir trumpesnis už 64 simbolius")
	@NotBlank (message = "Pavardė privaloma")
	private String surname;
	
	@Column(nullable = false, unique = true)
	@NotBlank( message = "Vartototjo vardas privalomas")
	private String username;
	
	@Column(nullable = false, unique = true)
	@Email(message = "El. pašto adresas turi būti tvarkingas")
	@NotBlank (message = "El. paštas privalomas")
	private String email;
	
	@Column(nullable = false)
	@NotBlank( message = "Slaptažodis privalomas")
	private String password;
	
	@Column()
	private String role="USER";
	
	@ManyToMany
	@JoinTable(
			  name = "userLesson", 
			  joinColumns = @JoinColumn(name = "user_id"), 
			  inverseJoinColumns = @JoinColumn(name = "lesson_id"))
	Set<Lesson> userLesson;
	
	public User() {
	}

	public User(String name, String surname, String username, @Email String email, String password, String role) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public User(String name, String surname, String username, @Email String email, String password) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(String name, String surname, String username, @Email String email, String password, String role,
			Set<Lesson> userLesson) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.userLesson = userLesson;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Lesson> getUserLesson() {
		return userLesson;
	}

	public void setUserLesson(Set<Lesson> userLesson) {
		this.userLesson = userLesson;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		HashSet<GrantedAuthority> auth=new HashSet<>();
		auth.add(new SimpleGrantedAuthority("ROLE_" + this.role));
		return auth;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
