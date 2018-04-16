package pl.com.tt.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pwykowski
 */
public class MockInvocationHandler<T, S> implements InvocationHandler {

	private Map<T, S> invocation = new HashMap<>();
	private T lastWhen;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		lastWhen = (T) args[0];
		invocation.putIfAbsent(lastWhen, (S) new Integer(0));
		return invocation.get(lastWhen);
	}

	public void put(S then) {
		this.invocation.put(lastWhen, then);
	}

}
