package pl.com.tt.model;

import java.util.List;

/**
 * Created by pwykowski
 */
public class StrictMovies implements IElement {

	private List<IElement> children;

	private String value;

	public StrictMovies(List<IElement> children) {
		this.children = children;
	}

	@Override
	public String getName() {
		return "movies";
	}

	@Override
	public List<IElement> getChildren() {
		return this.children;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
