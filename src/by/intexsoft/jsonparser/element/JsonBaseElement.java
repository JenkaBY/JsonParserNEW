/**
 * 
 */
package by.intexsoft.jsonparser.element;

import java.util.LinkedList;

/**
 * @author JenkaBY
 *
 */
public class JsonBaseElement {
	protected Object value;

	public JsonBaseElement() {
		if (isArray() || isJson()) {
			value = new LinkedList<JsonBaseElement>();
		}
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isJson() {
		return false;
	}

	public boolean isArray() {
		return false;
	}

	public boolean isString() {
		return false;
	}

	public boolean isNumber() {
		return false;
	}

	public boolean isNull() {
		return false;
	}

	public boolean isTrue() {
		return false;
	}

	public boolean isFalse() {
		return false;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
