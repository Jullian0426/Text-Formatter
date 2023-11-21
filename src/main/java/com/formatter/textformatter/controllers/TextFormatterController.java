package com.formatter.textformatter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TextFormatterController {

    @GetMapping("/")
    public String index() {
        return "index"; // Returns the index.html template
    }

    @PostMapping("/format-text")
    public String formatText(/* Add parameters to receive form data */) {
        // Add logic to handle text formatting
        return "result"; // Redirect to a result page or back to index
    }
}
