package by.intexsoft.jsonparser.element;

import java.util.LinkedList;
import java.util.List;

public class JsonElement extends JsonBaseElement {
	private List<StringElement> keys;

	public JsonElement() {
		keys = new LinkedList<>();
	}

	public void add(StringElement key, JsonBaseElement json) {
		keys.add(key);
		getValues().add(json);
	}

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
