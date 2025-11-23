package com.example.gestorTEDI.infrastructure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainWebController {


    @GetMapping
    public String getHomePage() {
        return "home"; // Referencia /resources/templates/home.html
    }
}