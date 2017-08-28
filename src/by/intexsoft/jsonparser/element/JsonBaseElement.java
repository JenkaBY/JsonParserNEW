package by.intexsoft.jsonparser.element;

import java.util.LinkedList;

/**
 * Базовый класс JsonBaseElement. От него наследуются все остальные классы
 * JsonElement
 */
public abstract class JsonBaseElement {
	protected Object value;

	/**
	 * Если класс наследник Array или Json создается список JsonBaseElement.
	 */
	public JsonBaseElement() {
		if (isArray() || isJson()) {
			value = new LinkedList<JsonBaseElement>();
		}
	}

	public Object getValue() {
		return value;
	}

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
