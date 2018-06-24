package app.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.models.User;
import app.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "${ORIGINS}", maxAge = 3600, allowCredentials = "true")
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/api/user")
	public List<User> findAllUsers(@RequestParam(value="username", required=false) String username) {
		if(username != null && !username.isEmpty())	{
			// Username was provided so search for that user
			return (List<User>) userRepository.findUserByUsername(username);
		} else { // username was not provided
			return (List<User>) userRepository.findAll();
		}		
	}
	
	@GetMapping("/api/user/{userId}")
	public User findUserById(@PathVariable("userId") int id) {
		Optional<User> u = userRepository.findById(id);
		if(u.isPresent()) {
			return u.get();
		}
		return null;
	}
	
	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser,
			HttpSession session) {
		Optional<User> data = userRepository.findById(userId);
		if(data.isPresent()) {
			User user = data.get();
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setRole(newUser.getRole());
			user.setPassword(newUser.getPassword());
			user.setPhone(newUser.getPhone());
			user.setEmail(newUser.getEmail());
			
			session.setAttribute("currentUser", user);
			return userRepository.save(user);
		}
		return null;
	}
	
	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		userRepository.deleteById(id);
	}
	
	@PostMapping("/api/user")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@GetMapping("/api/profile")
	public User profile(HttpSession session) {
		return (User) session.getAttribute("currentUser");
	}
	
	@PostMapping("/api/login")
	public List<User> login(@RequestBody User user, HttpSession session) {
		List<User> users = (List<User>) userRepository.findUserByCredentials(
				user.getUsername(), user.getPassword());

		if(users.size() == 1) {
			session.setAttribute("currentUser", users.get(0));
		}
		
		return users;
	}
	
	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session) {
		User u = userRepository.save(user);
		session.setAttribute("currentUser", u);
		return u;
	}
}
