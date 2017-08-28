/**
 * 
 */
package by.intexsoft.jsonparser.element;

/**
 * @author JenkaBY
 *
 */
public final class StringElement extends JsonBaseElement {

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public String toString() {
		return "\"" + super.toString() + "\"";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null && !(obj instanceof StringElement)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
}
