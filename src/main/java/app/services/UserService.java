package app.services;

import java.util.List;
import java.util.Optional;

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
@CrossOrigin(origins = "*", maxAge = 3600)
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
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser) {
		Optional<User> data = userRepository.findById(userId);
		if(data.isPresent()) {
			User user = data.get();
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setRole(newUser.getRole());
			user.setPassword(newUser.getPassword());
			user.setPhone(newUser.getPhone());
			user.setEmail(newUser.getEmail());
			
			userRepository.save(user);
			return user;
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
	
	@PostMapping("/api/login")
	public List<User> login(@RequestBody User user) {
		return (List<User>) userRepository.findUserByCredentials(user.getUsername(), user.getPassword());
	}
}
