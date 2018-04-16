package pl.com.tt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by pwykowski
 */
public class CalculusTest {

	@DisplayName("Will return 1000 for 10")
	@Test
	public void shouldReturn1000For10() {
		// given
		Calculus sut = mock(Calculus.class);
		given(sut.prepare(10)).willReturn(100);
		given(sut.power(10)).willCallRealMethod();
		// when
		int result = sut.power(10);
		// then
		assertEquals(1000, result);
	}

}