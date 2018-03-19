package transition.technologies.unittesting.mockito.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.wkr.fluentrule.api.FluentExpectedException;
import transition.technologies.unittesting.mockito.examples.Person.PersonBuilder;


public class PersonReportFormatterTest {

	@Rule
	public FluentExpectedException fluentThrown = FluentExpectedException.none();
	
	Person testperson1 = new PersonBuilder().name("name1").surname("surname1")
			.address("address1").age(30).city("Lodz").getPerson();

	Person testperson2 = new PersonBuilder().name("name2").surname("surname2")
			.address("address2").age(30).city("Lodz").getPerson();

	Person testperson3 = new PersonBuilder().name("name3").surname("surname3")
			.address("address2").age(10).city("Lodz").getPerson();
	
	Person testperson4 = new PersonBuilder().name("name4").surname("surname4")
			.address("address2").age(10).city("Warsaw").getPerson();
	
	@Mock
	PersonDao mockedPersonDao;
	
	@InjectMocks
	PersonReportFormatter sut;
	
	@Before
	public void setUp() throws PersonDaoException {
		MockitoAnnotations.initMocks(this);
		// general mocks initialization
	}
	
	@Test
	public void shouldReportForPersonContainNameAndSurname() throws PersonDaoException {
		when(mockedPersonDao.getPerson(anyInt())).thenReturn(testperson1);
		
		assertThat(sut.formatPerson(123))
			.contains(testperson1.getName())
			.contains(testperson1.getSurname());
		
	}
	
	@Test
	public void shouldCorrectlyFormatAgeReport() throws PersonDaoException {

	}
	
	@Test
	public void shouldReturnNoResultWhenNoPersonInGivenAgeRange() throws PersonDaoException {

	}
	
	@Test
	public void shouldReturnEmptyListWhenPersonDaoReturnsNull() throws PersonDaoException {
		when(mockedPersonDao.getAllPersons()).thenReturn(null);
		
		assertThat(sut.createFormattedPersonsInAgeRange(0, 20)).isEmpty();
	}
	
	@Test
	public void shouldThrowIllegalArgumentException() throws PersonDaoException {

	}
	
	@Test
	public void shouldThrowExceptionWhenPersonDaoThrowsException() throws PersonDaoException {
		
		fluentThrown.expect(PersonDaoException.class);
		when(mockedPersonDao.getAllPersons())
			.thenThrow(PersonDaoException.class);
		
		sut.createFormattedPersonsInAgeRange(0, 30);

	}
	
}
