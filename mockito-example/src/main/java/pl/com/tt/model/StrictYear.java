package pl.com.tt.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by pwykowski
 */
public class StrictYear implements IElement {
	
	private String value;

	public StrictYear(String value) {
		this.value = value;
	}

	@Override
	public String getName() {
		return "year";
	}

	@Override
	public List<IElement> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public String getValue() {
		return this.value;
	}
}