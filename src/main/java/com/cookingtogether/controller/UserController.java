package com.cookingtogether.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cookingtogether.User;
import com.cookingtogether.repository.UserRepo;
import com.cookingtogether.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для управления пользователями.
 * Предоставляет API для получения, сохранения, обновления и удаления пользователей.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepository;

    /**
     * Получает всех пользователей или одного пользователя по ID.
     *
     * @param id идентификатор пользователя (необязательный).
     * @return список всех пользователей или один пользователь.
     * @throws ResponseStatusException если пользователь с указанным ID не найден.
     */
    @GetMapping
    public ResponseEntity<Object> getUserOrAll(@RequestParam(name = "id", required = false) Long id) {
        if (id != null) {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER ID " + id + " NOT FOUND"));
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.ok(userRepository.findAll());
    }

    /**
     * Удаляет пользователя по ID.
     *
     * @param id идентификатор пользователя.
     * @return сообщение об успешном удалении или причина ошибки.
     */
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

    /**
     * Сохраняет нового пользователя или обновляет существующего.
     *
     * @param _method метод операции ("PUT" для обновления).
     * @param id      идентификатор пользователя (для обновления).
     * @param name    имя пользователя.
     * @param email   электронная почта пользователя.
     * @param pass    пароль пользователя.
     * @return сообщение о создании или обновлении пользователя.
     * @throws ResponseStatusException если пользователь для обновления не найден.
     */
    @PostMapping
    public ResponseEntity<String> saveUser(
            @RequestParam(required = false, name = "_method") String _method,
            @RequestParam(required = false, name = "id") Long id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "pass") String pass) {

        if ("PUT".equalsIgnoreCase(_method) && id != null) {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setUsername(name);
                user.setEmail(email);
                user.setPassword(pass);
                userRepository.save(user);
                return ResponseEntity.ok("USER UPDATED: " + user.getUsername());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
            }
        } else {
            User newUser = new User();
            newUser.setUsername(name);
            newUser.setEmail(email);
            newUser.setPassword(pass);
            userRepository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("USER CREATED: " + newUser.getUsername());
        }
    }
    
    /**
     * Получает имя текущего авторизованного пользователя.
     * 
     * @return имя пользователя или "anonymous", если пользователь не авторизован.
     */
    @GetMapping("/auth/currentUser")
    public String getCurrentUser() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
                User user = (User) authentication.getPrincipal();
                return user.getUsername();  // Возвращаем имя пользователя
            }
            return "anonymous";  // Возвращаем "anonymous", если пользователь не авторизован
        } catch (Exception e) {
            return "Error";  // Возвращаем ошибку, если возникла проблема
        }
    }

}
