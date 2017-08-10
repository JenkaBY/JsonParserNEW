/**
 * 
 */
package by.intexsoft.jsonparser.element;

/**
 * @author JenkaBY
 *
 */
public class StringElement extends JsonBaseElement {

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public String toString() {
		return "\"" + super.toString() + "\"";
	}

}
