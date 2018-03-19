package transition.technologies.unittesting.mockito.routefinder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.ConnectException;
import java.util.Arrays;

import javax.net.ssl.SSLException;
import javax.xml.ws.WebServiceException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import pl.wkr.fluentrule.api.FluentExpectedException;

import static 
transition.technologies.unittesting.
mockito.routefinder.RouteObtainerTestUtil.*;



@RunWith(MockitoJUnitRunner.class)
public class RouteObtainerServiceTest {

	@Rule
	public FluentExpectedException fluentThrown = FluentExpectedException.none();
	
	@Mock
	public RouteCache mockedRouteCache;
	
	@Mock
	public RouteCalculatorWebService mockedRouteCalculatorWebService;
	
	@InjectMocks
	public RouteObtainerService sut;
	
	@Before
	public void initialize() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldReturnRouteFromCache() throws CannotFindRouteException, ConnectException, WebServiceException, SSLException {
		
		//given
		Route testRoute = createTestRoute(1);
				
		when(mockedRouteCache.getRouteFromCache(
				any(Adress.class), any(Adress.class)))
			.thenReturn(testRoute);
		//when
		Route foundRoute = sut.getRoute(createTestAdress(1),
										createTestAdress(2));
		//then
		assertThat(foundRoute).isEqualTo(testRoute);
		
//		verify(mockedRouteCalculatorWebService, never())
//			.findRoute(anyObject(), anyObject());
	}
	
	@Test
	public void shouldAskWebServiceWhenCacheDoesNotContainRoute() throws ConnectException, WebServiceException, SSLException, CannotFindRouteException {
		//given
		Route testRoute = createTestRoute(1);
		when(mockedRouteCache.getRouteFromCache(
				anyObject(), anyObject()))
			.thenReturn(null);
		when(mockedRouteCalculatorWebService
				.findRoute(any(Adress.class), any(Adress.class)))
				.thenReturn(testRoute);
//		//when
		Route route = sut.getRoute(
				createTestAdress(1), 
				createTestAdress(2));
//		//then
		assertThat(route).isEqualTo(testRoute);
//		verify(mockedRouteCalculatorWebService, times(1))
//			.findRoute(anyObject(), anyObject());
	}
	
	@Test
	public void shouldPackLowLevelSocketExceptionInBusinessException() throws ConnectException, WebServiceException, SSLException, CannotFindRouteException {
		//given
		when(mockedRouteCache.getRouteFromCache(anyObject(), anyObject()))
			.thenReturn(null);
		when(mockedRouteCalculatorWebService.findRoute(anyObject(), anyObject()))
			.thenThrow(new WebServiceException());
		//when
		fluentThrown
			.expect(CannotFindRouteException.class)
			.hasRootCauseInstanceOf(WebServiceException.class);
		
		//then
		sut.getRoute(createTestAdress(1), 
				createTestAdress(2));

	}
	
}
