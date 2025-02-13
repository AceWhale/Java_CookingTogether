package com.cookingtogether;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки веб-запросов.
 * Управляет отображением страниц HTML.
 */
@Controller
public class WebController {

    /**
     * Обрабатывает запрос на страницу обратной связи.
     *
     * @param model объект Model для передачи данных в представление.
     * @return имя представления "feedback", которое будет отображаться пользователю.
     */
    @GetMapping("/feedback")
    public String getFeedback(Model model) {
        return "feedback";
    }
}
