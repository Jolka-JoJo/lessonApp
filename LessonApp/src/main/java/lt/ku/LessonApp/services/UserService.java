package lt.ku.LessonApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lt.ku.LessonApp.entities.User;
import lt.ku.LessonApp.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	public User addUser(User user) {
		user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
		
		return userRepository.save(user);
	}
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	public User updateUser(User user) {
		User old=userRepository.getById(user.getId());
		old.setName(user.getName());
		old.setSurname(user.getSurname());
		old.setEmail(user.getEmail());
		old.setUsername(user.getUsername());;
		old.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
		old.setRole(user.getRole());
		old.setUserLesson(user.getUserLesson());
		userRepository.save(old);
		return old;
	}
	
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	
	public User getUser(Integer id) {
		return userRepository.findById(id).get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Vartotojas nerastas");
		}
		return user;
	}
	

}
