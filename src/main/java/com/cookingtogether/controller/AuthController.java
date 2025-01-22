package com.cookingtogether.controller;

import com.cookingtogether.User;
import com.cookingtogether.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    //@PostMapping("/register")
    //public String register(User user, Model model) {
    //    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
    //        model.addAttribute("error", "Пользователь с таким именем уже существует.");
    //        return "register";
    //    }
    //   user.setPass(passwordEncoder.encode(user.getPassword()));
    //    userRepository.save(user);
    //    return "redirect:/login";
    //}
}
