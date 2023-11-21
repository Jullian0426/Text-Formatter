package com.formatter.textformatter;

import com.formatter.textformatter.services.TextFormatterService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
