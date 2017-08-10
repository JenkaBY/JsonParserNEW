/**
 * 
 */
package by.intexsoft.jsonparser.element;

/**
 * @author JenkaBY
 *
 */
public class FalseElement extends JsonBaseElement {

	public boolean equals(Object o) {
		if (o == null || !(o instanceof FalseElement)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isFalse() {
		return true;
	}
	
	@Override
	public String toString() {
		return "false";
	}

	@Override
	public Object getValue() {
		return false;
	}
}
