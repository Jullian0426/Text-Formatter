package com.formatter.textformatter.controllers;

import com.formatter.textformatter.services.TextFormatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TextFormatterController {

    @Autowired
    private TextFormatterService textFormatterService;

    @GetMapping("/")
    public String index() {
        return "index"; // Returns the index.html template
    }

    @PostMapping("/format-text")
    public String formatText(@RequestParam("textInput") String text,
                             @RequestParam("action") String action,
                             Model model) {
        String formattedText = "";
        switch (action) {
            case "reverse":
                formattedText = textFormatterService.reverseText(text);
                break;
            case "sort":
                formattedText = textFormatterService.sortText(text);
                break;
            // Handle other cases
        }
        model.addAttribute("formattedText", formattedText);
        return "result"; // Create a 'result.html' template to display the result
    }
}
