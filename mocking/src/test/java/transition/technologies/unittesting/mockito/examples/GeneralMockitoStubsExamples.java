package transition.technologies.unittesting.mockito.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import pl.wkr.fluentrule.api.FluentExpectedException;
import transition.technologies.unittesting.mockito.examples.Person.PersonBuilder;

public class GeneralMockitoStubsExamples {
	
	@Rule
	public FluentExpectedException fluentThrown = FluentExpectedException.none();
	
	Person testperson1 = new PersonBuilder().name("name1").surname("surname1")
			.address("address1").getPerson();

	Person testperson2 = new PersonBuilder().name("name2").surname("surname2")
			.address("address2").getPerson();

	Person deactivatedPerson = new PersonBuilder().name("deactivated")
			.surname("deactivated").getPerson();
	
	@Test
	public void shouldReturnProgrammedValue() {
		//given
		Iterator<String> mockedIterator = mock(Iterator.class);
		when(mockedIterator.next())
			.thenReturn("Hello")
			.thenReturn(" ")
			.thenReturn("World");
		//when
		String result = mockedIterator.next() + mockedIterator.next() + mockedIterator.next();
		//then
		assertThat(result).isEqualTo("Hello World");
	}
	
	@Test
	public void shouldMockIteratorCorrectly() {
		//given
		Iterator<String> mockedIterator = mock(Iterator.class);
		
		when(mockedIterator.hasNext())
			.thenReturn(true,true,true,false);
		
		when(mockedIterator.next())
			.thenReturn("Hello", " ", "World");
		//when
		StringBuilder resultBuilder = new StringBuilder();
		while (mockedIterator.hasNext()) {
			resultBuilder.append(mockedIterator.next());
		}
		//then
		assertThat(resultBuilder.toString()).isEqualTo("Hello World");

	}

	@Test
	public void shouldReturnProgrammedValue_withArguments() throws PersonDaoException {
		//given
		PersonReportFormatter mockedPersonFormatter = mock(PersonReportFormatter.class);
		
		List<String> citiesList = Arrays.asList("Lodz", "Warszawa", "Kielce");
		when(mockedPersonFormatter.createFormattedPersonsFromCities(anyCollection()))
			.thenReturn("Andy");

		when(mockedPersonFormatter.createFormattedPersonsFromCities(citiesList))
			.thenReturn("Lukasz, Kinga");
		
		
		//when
		String reportFromEmptyList = mockedPersonFormatter.createFormattedPersonsFromCities(new ArrayList<String>());
		String reportFromCorrectList = mockedPersonFormatter.
				createFormattedPersonsFromCities(citiesList);
		
		//then
		assertThat(reportFromEmptyList).isEqualTo("Andy");
		assertThat(reportFromCorrectList).isEqualTo("Lukasz, Kinga");

	}
	
	
	@Test
	public void shoudlDaoReturnProgrammedValue() throws PersonDaoException {
		//given
			
		PersonDao mockedPersonDao = mock(PersonDao.class);
		when(mockedPersonDao.findByName(anyString())).thenReturn(null);
		when(mockedPersonDao.findByName("name1")).thenReturn(testperson1);
		when(mockedPersonDao.findByName("name2")).thenReturn(testperson2);
		
		when(mockedPersonDao.findByName(argThat(new ArgumentMatcher<String>() {
			@Override
			public boolean matches(Object argument) {
				return argument != null && ((String) argument).endsWith("_deactivated");
			}
		})))
		.thenReturn(deactivatedPerson);
		
		when(mockedPersonDao.findByName(isNull(String.class))).
		
		//when
		thenThrow(new PersonDaoException(new IllegalArgumentException()));
		
		//then
		assertThat(mockedPersonDao.findByName("name1")).isEqualTo(testperson1);
		assertThat(mockedPersonDao.findByName("name2")).isEqualTo(testperson2);
		assertThat(mockedPersonDao.findByName("name3_deactivated")).isEqualTo(deactivatedPerson);
		assertThat(mockedPersonDao.findByName("NON_EXISTING_NAME")).isNull();
		
	}
	
	@Test
	public void shouldCorrectlyMockVoidMethod() throws PersonDaoException {
		//given
		PersonDao mockedPersonDao = mock(PersonDao.class);
		doThrow(new PersonDaoException(new IllegalArgumentException())).
			when(mockedPersonDao).deletePerson(isNull(Person.class));
		
		fluentThrown.expect(PersonDaoException.class).hasRootCauseInstanceOf(IllegalArgumentException.class);
		//when
		mockedPersonDao.deletePerson(null);
		//then

	}
	
	
	@Test
	public void shouldShowDifferenceBetweenMockAndSpy() {
		
		LinkedList<String> mockedList = mock(LinkedList.class);
		mockedList.add("one");
		mockedList.add("two");
		
		when(mockedList.size()).thenReturn(3);
		
		assertThat(mockedList.size()).isEqualTo(3);
		assertThat(mockedList.getFirst()).isNull();
		
		LinkedList<String> spiedList = spy(new LinkedList<String>());
		spiedList.add("one");
		spiedList.add("two");
		
		when(spiedList.size()).thenReturn(3);
		assertThat(spiedList.getFirst()).isEqualTo("one");
		assertThat(spiedList.size()).isEqualTo(3);
		
	}
	
	
	@Test
	public void shouldShowHowVerifyWorks() {
		//given
		LinkedList<String> mockedList = mock(LinkedList.class);
		mockedList.add("once");
		mockedList.add("twice");
		mockedList.add("twice");
		
		verify(mockedList, times(1)).add("once");
		verify(mockedList, times(2)).add("twice");
		verify(mockedList, times(0)).add("not even once");
		verify(mockedList, never()).add("not even once");
		//when
		//then

	}
	
	
	
	
}
