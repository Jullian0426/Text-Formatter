package com.formatter.textformatter.controllers;

import com.formatter.textformatter.services.TextAnalysisResult;
import com.formatter.textformatter.services.TextFormatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class TextFormatterController {

    @Autowired
    private TextFormatterService textFormatterService;

    @GetMapping("/")
    public String index() {
        return "index"; // Returns the index.html template
    }

    @PostMapping("/format")
    public String formatText(@RequestParam(value = "textInput", required = false) String text,
                             @RequestParam(value = "fileUpload", required = false) MultipartFile file,
                             @RequestParam(value = "reverse", defaultValue = "false") boolean reverse,
                             @RequestParam(value = "analyzeText", defaultValue = "false") boolean analyze,
                             Model model) {
        String formattedText = text;

        // Handle file upload if a file is provided
        if (file != null && !file.isEmpty()) {
            try {
                formattedText = new String(file.getBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                model.addAttribute("error", "Error processing file: " + e.getMessage());
                return "result"; // Redirect to a result page showing the error
            }
        }

        // Apply text formatting based on selected options
        if (reverse) {
            formattedText = textFormatterService.reverseText(formattedText);
        }

        // Analyze text if the checkbox is selected
        if (analyze) {
            TextAnalysisResult analysisResult = textFormatterService.analyzeText(formattedText);
            model.addAttribute("analysisResult", analysisResult);
        }

        model.addAttribute("formattedText", formattedText);
        return "result";
    }
}
