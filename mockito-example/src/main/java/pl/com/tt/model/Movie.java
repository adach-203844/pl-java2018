package pl.com.tt.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Objects;

/**
 * Created by pwykowski
 */
@XStreamAlias("movie")
public class Movie implements Comparable<Movie> {

	private String title;

	private String year;

	private String country;

	public Movie(String title, String year, String country) {
		this.title = title;
		this.year = year;
		this.country = country;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getCountry() {
		return country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Movie movie = (Movie) o;
		return Objects.equals(title, movie.title);
	}

	@Override
	public int hashCode() {

		return Objects.hash(title);
	}

	@Override
	public int compareTo(Movie o) {
		return this.getTitle().compareTo(o.getTitle());
	}
}
