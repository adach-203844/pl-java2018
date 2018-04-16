package pl.com.tt.model;

import java.util.List;

/**
 * Created by pwykowski
 */
public class StrictMovie implements IElement {

	private String value;

	private List<IElement> children;

	public StrictMovie(List<IElement> children) {
		this.children = children;
	}

	@Override
	public String getName() {
		return "movie";
	}

	@Override
	public List<IElement> getChildren() {
		return this.getChildren();
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
