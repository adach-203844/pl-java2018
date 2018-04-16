package pl.com.tt.mock;

/**
 * Created by pwykowski
 */
public class Stubbing<T> {

	private MockInvocationHandler handler;

	public Stubbing(MockInvocationHandler handler) {
		this.handler = handler;
	}

	public void thenReturn(T then) {
		handler.put(then);
	}

}
