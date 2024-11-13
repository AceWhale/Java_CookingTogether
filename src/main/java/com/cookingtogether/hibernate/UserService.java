package com.cookingtogether.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public User findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("USER ID " + id + " NOT FOUND"));
	}

	@Transactional(readOnly = true)
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	@Transactional
	public void saveUser(String name, String email, String pass) {
		User newUser = new User();
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPass(pass);
		userRepository.save(newUser);
	}

	@Transactional
	public void updateUser(Long id, String name, String email) {
		User user = findUserById(id);
		user.setName(name);
		user.setEmail(email);
		userRepository.save(user);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new EntityNotFoundException("USER ID " + id + " NOT FOUND");
		}
		userRepository.deleteById(id);
	}
}