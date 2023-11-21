package com.formatter.textformatter.controllers;

import com.formatter.textformatter.services.TextAnalysisResult;
import com.formatter.textformatter.services.TextFormatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class TextFormatterController {

    @Autowired
    private TextFormatterService textFormatterService;

    @GetMapping("/")
    public String index() {
        return "index"; // Returns the index.html template
    }

    @GetMapping("/download/{fileIdentifier}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileIdentifier) {
        String filePath = fileMappings.get(fileIdentifier);
        if (filePath == null) {
            throw new RuntimeException("File not found");
        }

        try {
            Path file = Paths.get(filePath);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    private static final String FILE_DIRECTORY = "src/main/resources/results";
    private Map<String, String> fileMappings = new ConcurrentHashMap<>();

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

        // Save the formatted text to a file
        try {
            String filename = "formatted_text_" + UUID.randomUUID().toString() + ".txt";
            Path path = Paths.get(FILE_DIRECTORY, filename);
            Files.write(path, formattedText.getBytes());

            String fileIdentifier = UUID.randomUUID().toString();
            fileMappings.put(fileIdentifier, path.toString());

            model.addAttribute("fileIdentifier", fileIdentifier);
        } catch (IOException e) {
            model.addAttribute("error", "Error creating file: " + e.getMessage());
        }

        final int PREVIEW_LENGTH = 500; // Define a max length for the preview
        String previewText = formattedText.length() > PREVIEW_LENGTH
                ? formattedText.substring(0, PREVIEW_LENGTH) + "..."
                : formattedText;

        model.addAttribute("formattedText", formattedText);
        model.addAttribute("previewText", previewText);
        return "result";
    }
}
