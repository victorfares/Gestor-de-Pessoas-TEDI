package com.example.gestorTEDI.infrastructure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") // Responde à raiz do site
public class MainWebController {

    /**
     * GET /
     * Mostra a página "home.html" como a página principal.
     */
    @GetMapping
    public String getHomePage() {
        return "home"; // Referencia /resources/templates/home.html
    }
}