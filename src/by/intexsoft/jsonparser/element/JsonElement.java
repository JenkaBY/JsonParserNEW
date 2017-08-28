package by.intexsoft.jsonparser.element;

import java.util.LinkedList;
import java.util.List;

public final class JsonElement extends JsonBaseElement {
	private List<StringElement> keys;

	public JsonElement() {
		keys = new LinkedList<>();
	}

	/**
	 * Добавляет пары ключ и значение.
	 * 
	 * @param key
	 *            StringElement
	 * @param json
	 *            JsonBaseElement
	 */
	public void add(StringElement key, JsonBaseElement json) {
		keys.add(key);
		getValues().add(json);
	}

	/*
	 * Метод ничего не делает. Не следуюет его использовать для JsonElement
	 */
	@Override
	public void setValue(Object value) {
	}

	@Override
	public boolean isJson() {
		return true;
	}

	@Override
	public String toString() {
		return "{" + join() + "}";
	}

	private String join() {
		StringBuilder joined = new StringBuilder();
		for (int index = 0; index < keys.size(); index++) {
			joined.append(keys.get(index)).append(":").append(getValues().get(index));
			if (index != keys.size() - 1) {
				joined.append(",");
			}
		}
		return joined.toString();
	}

	@SuppressWarnings("unchecked")
	private LinkedList<JsonBaseElement> getValues() {
		return (LinkedList<JsonBaseElement>) getValue();
	}

	public JsonBaseElement getByKey(String key) {
		int indexOfKey = getIndexOfKey(key);
		if (indexOfKey != -1) {
			return getValues().get(indexOfKey);
		}
		return null;
	}

	private int getIndexOfKey(String key) {
		StringElement keyObject = new StringElement();
		keyObject.setValue(key);
		return keys.indexOf(keyObject);
	}
}
