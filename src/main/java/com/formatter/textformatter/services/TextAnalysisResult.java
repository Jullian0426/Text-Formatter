package com.formatter.textformatter.services;

import java.util.Map;

public class TextAnalysisResult {
    private int wordCount;
    private Map<String, Integer> wordFrequency;

    public int getWordCount() {
        return wordCount;
    }

    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public void setWordFrequency(Map<String, Integer> wordFrequency) {
        this.wordFrequency = wordFrequency;
    }

    public TextAnalysisResult(int wordCount, Map<String, Integer> wordFrequency) {
        this.wordCount = wordCount;
        this.wordFrequency = wordFrequency;
    }
}
