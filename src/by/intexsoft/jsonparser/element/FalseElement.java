/**
 * 
 */
package by.intexsoft.jsonparser.element;

import static java.lang.Boolean.FALSE;

/**
 * @author JenkaBY
 *
 */
public final class FalseElement extends JsonBaseElement {

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
		return FALSE.toString();
	}

	@Override
	public Object getValue() {
		return false;
	}
}
