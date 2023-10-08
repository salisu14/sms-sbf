package com.sulbasoft.mbims.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // This method maps to the root URL ("/") and returns the name of the HTML template to render
        return "index";
    }
}
