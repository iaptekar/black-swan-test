package com.blackswan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository repository;

	@GetMapping("")
	public List<User> getUsers() {
		return repository.findAll();
	}

	@GetMapping("/user/{id}")
	public User getUser(@PathVariable("id") int id) {
		return repository.getOne(id);
	}

	@PostMapping("/user")
	public User createUser(User user) {
		return repository.save(user);
	}

	@DeleteMapping("/user/{id}")
	public void deleteUserByName(@PathVariable("id") int id) {
		repository.deleteById(id);
	}

	@PutMapping("/user")
	public User updateUser(User user) {
		return repository.save(user);
	}
}
