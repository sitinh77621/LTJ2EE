package com.example.BaiTap2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;



@Controller
public class HomeController {

    @GetMapping("/hello")
    public String index(Model model) {
        model.addAttribute("message", "Day la message tu Controller");
        model.addAttribute("title", "Trang chu");
        return "index";
    }
}