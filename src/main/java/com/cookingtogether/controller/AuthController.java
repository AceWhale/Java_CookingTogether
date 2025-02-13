package com.cookingtogether.controller;

import com.cookingtogether.User;
import com.cookingtogether.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для управления аутентификацией пользователей.
 * Включает маршруты для регистрации и входа пользователя.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор контроллера для аутентификации.
     * 
     * @param userRepo Репозиторий для работы с пользователями.
     * @param passwordEncoder Шифратор паролей для кодирования паролей.
     */
    public AuthController(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Отображает страницу регистрации.
     * 
     * @param model Модель для передачи данных на страницу.
     * @return Имя HTML-шаблона для регистрации.
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // Создание нового объекта пользователя для формы регистрации
        return "register"; // Шаблон регистрации
    }

    /**
     * Обрабатывает регистрацию нового пользователя.
     * Шифрует пароль и сохраняет пользователя в базе данных.
     * 
     * @param user Объект пользователя, введенный в форму регистрации.
     * @return Редирект на страницу логина после успешной регистрации.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифрование пароля перед сохранением
        userRepo.save(user); // Сохранение нового пользователя в базе данных
        return "redirect:/auth/login"; // Перенаправление на страницу логина
    }

    /**
     * Отображает страницу логина.
     * 
     * @return Имя HTML-шаблона для страницы входа.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Шаблон страницы входа
    }
}
