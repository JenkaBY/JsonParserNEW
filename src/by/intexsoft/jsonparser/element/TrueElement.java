/**
 * 
 */
package by.intexsoft.jsonparser.element;

/**
 * @author JenkaBY
 *
 */
public class TrueElement extends JsonBaseElement {
	
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
		return "true";
	}

	@Override
	public Object getValue() {
		return true;
	}
}
