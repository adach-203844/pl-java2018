package pl.com.tt.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.TreeSet;

/**
 * Created by pwykowski
 */
@XStreamAlias("movies")
public class Movies {

	@XStreamImplicit
	private TreeSet<Movie> movieList;

	public Movies(TreeSet<Movie> movieList) {
		this.movieList = movieList;
	}

	public TreeSet<Movie> getMovieList() {
		return movieList;
	}
}
