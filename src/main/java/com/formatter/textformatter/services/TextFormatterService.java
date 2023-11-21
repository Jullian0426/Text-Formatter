package com.formatter.textformatter.services;

import org.springframework.stereotype.Service;

@Service
public class TextFormatterService {

    public String reverseText(String input) {
        // Logic to reverse the text
        return new StringBuilder(input).reverse().toString();
    }

    public String sortText(String input) {
        return input;
    }

    // Additional methods for other formatting actions
}
