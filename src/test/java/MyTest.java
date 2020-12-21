import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	JavaFXTemplate a1;
	JavaFXTemplate a2;
	@Test
	void test() {
		assertEquals(false, a1.welcomePane(), "should not be initialized");
	}
	
	void test2() {
		a2.welcomeScene();
		assertEquals(true, a1.welcomePane(), "should be initialized");
	}
}
