package de.carina.likezerotohero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  // HTML Views
@RequestMapping("/")  // ← Base-Path für öffentliche Seiten
public class PublicController {
    @GetMapping("/")
    public String index() {
        return "index";  // templates/index.html
    }
}
