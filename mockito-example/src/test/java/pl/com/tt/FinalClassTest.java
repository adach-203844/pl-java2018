package pl.com.tt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by pwykowski
 */
class FinalClassTest {

	@Test
	public void shouldOvercomeFinalMethod() {
		// given
		FinalClass original = new FinalClass();
		FinalClass finalClassMock = mock(FinalClass.class);
		given(finalClassMock.evilMethod()).willReturn("God blessing!");
		// when
		String result = finalClassMock.evilMethod();
		// then
		assertNotEquals(original.evilMethod(), result);
	}

}