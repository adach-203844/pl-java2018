package pl.com.tt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pwykowski
 */
class CalculatorTest {

	@DisplayName("(╯°□°）╯︵ ┻━┻")
	@RepeatedTest(10)
	public void shouldReturn20For10And2() {
		Calculator calculator = mock(Calculator.class);
		when(calculator.calculate(2, 10)).thenReturn(90);

		int result = calculator.calculate(2, 10);
		assertEquals(90, result);
	}

}