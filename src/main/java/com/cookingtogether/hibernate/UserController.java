package com.cookingtogether.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepository;

	@GetMapping
	public ResponseEntity<Object> getUserOrAll(@RequestParam(name = "id", required = false) Long id) {
		if (id != null) {
			User user = userRepository.findById(id).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER ID " + id + " NOT FOUND"));
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.ok(userRepository.findAll());
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteUser(@RequestParam(name = "id") Long id) {
		try {
			userService.deleteUser(id);
			return ResponseEntity.ok("USER DELETED");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.ok("USER ID " + id + " NOT FOUND");
		} catch (OptimisticLockException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT SERVICE CAUSE VERSION: " + id);
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("ERROR - FAILED DELETE USER: " + e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<String> saveUser(@RequestParam(required = false, name = "_method") String _method,
			@RequestParam(required = false, name = "id") Long id, @RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email, 
			@RequestParam(name = "pass") String pass) {
		if ("PUT".equalsIgnoreCase(_method) && id != null) {
			return userRepository.findById(id).map(user -> {
				user.setName(name);
				user.setEmail(email);
				user.setPass(pass);
				userRepository.save(user);
				return ResponseEntity.ok("USER UPDATED: " + user.getName());
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND"));
		} else {
			var newUser = new User();
			newUser.setName(name);
			newUser.setEmail(email);
			newUser.setPass(pass);
			userRepository.save(newUser);
			return ResponseEntity.status(HttpStatus.CREATED).body("USER CREATED: " + newUser.getName());
		}
	}
}