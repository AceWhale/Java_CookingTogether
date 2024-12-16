package com.cookingtogether.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки веб-запросов.
 * Управляет отображением страниц HTML.
 */
@Controller
public class WebController {

    /**
     * Обрабатывает запрос к главной странице.
     *
     * @return имя шаблона главной страницы ("index").
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Обрабатывает запрос к странице регистрации.
     *
     * @return имя шаблона страницы регистрации ("register").
     */
    @GetMapping("register")
    public String register() {
        return "register";
    }
}
