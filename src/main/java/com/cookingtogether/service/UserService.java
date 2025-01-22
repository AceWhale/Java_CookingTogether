package com.cookingtogether.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.cookingtogether.User;
import com.cookingtogether.repository.UserRepo;

import java.util.List;

/**
 * Сервис для управления пользователями.
 * Обеспечивает бизнес-логику для операций с сущностью {@link User}.
 */
@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return пользователь с указанным ID.
     * @throws EntityNotFoundException если пользователь с таким ID не найден.
     */
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("USER ID " + id + " NOT FOUND"));
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список всех пользователей.
     */
    @Transactional(readOnly = true)
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
    
    
    /**
     * Создает нового пользователя и сохраняет его в базе данных.
     *
     * @param name  имя пользователя.
     * @param email электронная почта пользователя.
     * @param pass  пароль пользователя.
     */
    @Transactional
    public void saveUser(String name, String email, String pass) {
        User newUser = new User();
        newUser.setUsername(name);
        newUser.setEmail(email);
        newUser.setPass(pass);
        userRepository.save(newUser);
    }

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param id    идентификатор пользователя.
     * @param name  новое имя пользователя.
     * @param email новая электронная почта пользователя.
     * @throws EntityNotFoundException если пользователь с таким ID не найден.
     */
    @Transactional
    public void updateUser(Long id, String name, String email) {
        User user = findUserById(id);
        user.setUsername(name);
        user.setEmail(email);
        userRepository.save(user);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя.
     * @throws EntityNotFoundException если пользователь с таким ID не найден.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("USER ID " + id + " NOT FOUND");
        }
        userRepository.deleteById(id);
    }
}
