package transition.technologies.unittesting.mockito.examples;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

import transition.technologies.unittesting.mockito.examples.Person.PersonBuilder;

public class PersonBuilderTest {

	@Test
	public void shouldBuilderCreateCorrectInstance() {
		//given
		PersonBuilder sut = new PersonBuilder();
		long testdateLong = 1234454456;
		String name = "testname";
		String surname = "testsurname";
		String addess = "testaddress";
		String city = "testcity";
		String country = "testcountry";
		int age = 10;
		//when
		sut
			.name(name)
			.surname(surname)
			.address(addess)
			.city(city)
			.country(country)
			.age(age)
			.lastReadForReporting(new Date(testdateLong));
		
		//then
		Person person = sut.getPerson();
		
		assertThat(person.getName()).isEqualTo(name);
		assertThat(person.getSurname()).isEqualTo(surname);
		assertThat(person.getCity()).isEqualTo(city);
		assertThat(person.getCountry()).isEqualTo(country);
		assertThat(person.getAddress()).isEqualTo(addess);
		assertThat(person.getAge()).isEqualTo(age);
		assertThat(person.getLastReadForReporting()).isEqualTo(new Date(testdateLong));
	}
	
}
