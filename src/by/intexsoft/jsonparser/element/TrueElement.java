/**
 * 
 */
package by.intexsoft.jsonparser.element;

import static java.lang.Boolean.TRUE;

/**
 * @author JenkaBY
 *
 */
public final class TrueElement extends JsonBaseElement {

	public boolean equals(Object o) {
		if (o == null || !(o instanceof TrueElement)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isTrue() {
		return true;
	}

	@Override
	public String toString() {
		return TRUE.toString();
	}

	@Override
	public Object getValue() {
		return true;
	}
}
