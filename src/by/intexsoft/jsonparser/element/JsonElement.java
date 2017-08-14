package by.intexsoft.jsonparser.element;

import java.util.LinkedList;
import java.util.List;

public class JsonElement extends JsonBaseElement {
	private List<StringElement> keys;

	public JsonElement() {
		keys = new LinkedList<>();
	}

	
	/**
	 * Добавляет пары ключ и значение. 
	 * @param key StringElement 
	 * @param json JsonBaseElement
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
		}
		return joined.toString();
	}

	@SuppressWarnings("unchecked")
	public LinkedList<JsonBaseElement> getValues() {
		return (LinkedList<JsonBaseElement>) value;
	}
}
