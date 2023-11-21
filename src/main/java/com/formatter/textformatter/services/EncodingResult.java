package com.formatter.textformatter.services;

public class EncodingResult {
    private String encodedText;
    private int shiftKey;

    // Constructors, getters, setters
    public String getEncodedText() {
        return encodedText;
    }

    public int getShiftKey() {
        return shiftKey;
    }

    public void setEncodedText(String encodedText) {
        this.encodedText = encodedText;
    }

    public void setShiftKey(int shiftKey) {
        this.shiftKey = shiftKey;
    }

    public EncodingResult(String encodedText, int shiftKey) {
        this.encodedText = encodedText;
        this.shiftKey = shiftKey;
    }
}

