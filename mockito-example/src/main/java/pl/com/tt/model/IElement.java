package pl.com.tt.model;

import java.util.List;

/**
 * Created by pwykowski
 */
public interface IElement {

	String getName();
	List<IElement> getChildren();
	String getValue();

}
