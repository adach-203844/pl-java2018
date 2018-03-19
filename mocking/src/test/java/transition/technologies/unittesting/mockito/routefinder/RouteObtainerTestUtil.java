package transition.technologies.unittesting.mockito.routefinder;

import java.util.Arrays;

public class RouteObtainerTestUtil {
	
	public static Adress createTestAdress(int index) {
		return new Adress("Pol"+index, "Lodz"+index, "0"+index, "s1"+index, index, index);
	}
	
	public static Route createTestRoute(int index) {
		return new Route(
				createTestAdress(1),
				createTestAdress(2),
				Arrays.asList(createTestAdress(3)));
	}

}
