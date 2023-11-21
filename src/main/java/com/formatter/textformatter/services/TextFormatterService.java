package com.formatter.textformatter.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TextFormatterService {

    public String reverseText(String input) {
        // Logic to reverse the text
        return new StringBuilder(input).reverse().toString();
    }

    public TextAnalysisResult analyzeText(String text) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        // Remove non-alphabetic characters (except spaces) and split by spaces
        String[] words = text.replaceAll("[\r\n]+", " ").replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        int wordCount = words.length;
        for (String word : words) {
            if (!word.isEmpty()) { // Check if the word is not empty after trimming non-alphabetic characters
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        return new TextAnalysisResult(wordCount, frequencyMap);
    }

    public String encodeText(String input, int shift) {
        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base + shift) % 26 + base);
            }
            encoded.append(c);
        }
        return encoded.toString();
    }

    public String decodeText(String encodedText, int shift) {
        return encodeText(encodedText, 26 - shift); // For a simple Caesar cipher
    }
}
