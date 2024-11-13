package com.cookingtogether.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("register")
    public String register() {
    	return "register";
    }
}