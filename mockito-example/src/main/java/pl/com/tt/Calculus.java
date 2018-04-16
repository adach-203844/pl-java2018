package pl.com.tt;

/**
 * Hello world!
 */
public interface Calculus {

	int prepare(int a);

	/**
	 * Calculates the power of given number
	 *
	 * @param a the number
	 * @return power for a given number
	 */
	default int power(int a) {
		return prepare(a) * a;
	}

}
