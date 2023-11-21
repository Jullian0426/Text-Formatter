package com.formatter.textformatter;

import com.formatter.textformatter.services.TextAnalysisResult;
import com.formatter.textformatter.services.TextFormatterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TextFormatterApplicationTests {

	private TextFormatterService textFormatterService;

	@BeforeEach
	void setUp() {
		textFormatterService = new TextFormatterService();
	}

	@Test
	public void testReverseText() {
		String originalText = "Hello World";
		String expectedReversedText = "dlroW olleH";
		String actualReversedText = textFormatterService.reverseText(originalText);

		assertEquals(expectedReversedText, actualReversedText);
	}

	@Test
	public void testAnalyzeText() {
		String testText = "Hello how are you? hello";
		TextAnalysisResult result = textFormatterService.analyzeText(testText);

		// Test the word count
		assertEquals(5, result.getWordCount(), "Word count should be 5");

		// Test the frequency of individual words (case-insensitive and symbols removed)
		Map<String, Integer> frequencyMap = result.getWordFrequency();
		assertEquals(2, frequencyMap.getOrDefault("hello", 0), "Frequency of 'hello' should be 2");
		assertEquals(1, frequencyMap.getOrDefault("how", 0), "Frequency of 'how' should be 1");
		assertEquals(1, frequencyMap.getOrDefault("are", 0), "Frequency of 'are' should be 1");
		assertEquals(1, frequencyMap.getOrDefault("you", 0), "Frequency of 'you' should be 1");

		// Ensure that symbols are removed
		assertFalse(frequencyMap.containsKey("you?"), "'you?' should not be a key in the frequency map");
	}

	@Test
	void testCaesarCipherEncode() {
		String originalText = "abc";
		int shift = 3;
		boolean isEncoding = true;
		String expectedEncodedText = "def"; // 'abc' shifted by 3

		String actualEncodedText = textFormatterService.caesarCipher(originalText, shift, isEncoding);

		assertEquals(expectedEncodedText, actualEncodedText, "The encoded text should shift each character by 3");
	}

	@Test
	void testCaesarCipherDecode() {
		String encodedText = "def";
		int shift = 3;
		boolean isEncoding = false;
		String expectedDecodedText = "abc"; // 'def' reversed by shift of 3

		String actualDecodedText = textFormatterService.caesarCipher(encodedText, shift, isEncoding);

		assertEquals(expectedDecodedText, actualDecodedText, "The decoded text should reverse the shift of 3");
	}
}
