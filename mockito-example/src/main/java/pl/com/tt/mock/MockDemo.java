package pl.com.tt.mock;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.platform.commons.annotation.Testable;
import pl.com.tt.Calculator;
import pl.com.tt.Controller;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by pwykowski
 */
public class MockDemo {

	public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

		Calculator dynamicType = new ByteBuddy()
				.subclass(Calculator.class)
				.method(ElementMatchers.named("toString"))
				.intercept(FixedValue.value("Hello World!"))
				.make()
				.load(MockDemo.class.getClassLoader())
				.getLoaded()
				.getDeclaredConstructor()
				.newInstance();

		System.out.println(dynamicType);

		// Mocking interface using Java Proxy
		Calculator mock = Mock.mock(Calculator.class);
		Mock.when(mock.calculate(10, 20)).thenReturn(100);
		System.out.println(mock.calculate(10, 20));

		// Mocking class using ByteBuddy
		Controller mockedController = Mock.mock(Controller.class);
		Mock.when(mockedController.process("GET /home")).thenReturn(404);
		System.out.println(mockedController.process("GET /home"));
	}

}
