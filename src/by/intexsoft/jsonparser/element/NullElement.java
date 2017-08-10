/**
 * 
 */
package by.intexsoft.jsonparser.element;

/**
 * @author JenkaBY
 *
 */
public class NullElement extends JsonBaseElement {
	
	
	@Override
	public boolean isNull() {
		return true;
	}

	@Override
	public String toString() {
		return "null";
	}

	@Override
	public Object getValue() {
		return null;
	}
}
