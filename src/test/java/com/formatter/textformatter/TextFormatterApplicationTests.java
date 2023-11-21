package com.formatter.textformatter;

import com.formatter.textformatter.services.TextAnalysisResult;
import com.formatter.textformatter.services.TextFormatterService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TextFormatterApplicationTests {

	private final TextFormatterService service = new TextFormatterService();

	@Test
	public void testReverseText() {
		String originalText = "Hello World";
		String expectedReversedText = "dlroW olleH";
		String actualReversedText = service.reverseText(originalText);

		assertEquals(expectedReversedText, actualReversedText);
	}

	@Test
	public void testAnalyzeText() {
		String testText = "Hello how are you? hello";
		TextAnalysisResult result = service.analyzeText(testText);

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
}
