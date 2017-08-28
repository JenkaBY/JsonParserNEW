package by.intexsoft.jsonparser.factory;

import by.intexsoft.jsonparser.element.ArrayElement;
import by.intexsoft.jsonparser.element.FalseElement;
import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.element.JsonElement;
import by.intexsoft.jsonparser.element.NullElement;
import by.intexsoft.jsonparser.element.NumberElement;
import by.intexsoft.jsonparser.element.StringElement;
import by.intexsoft.jsonparser.element.TrueElement;
import by.intexsoft.jsonparser.parser.validator.helper.Element;

/**
 * Фабрика - синглтон, создающая JsonElement по согласно типу Element.
 */
public final class JsonElementFactory {
	private static JsonElementFactory instance;

	private JsonElementFactory() {
	}

	public static JsonElementFactory getInstance() {
		if (instance == null) {
			instance = new JsonElementFactory();
		}
		return instance;
	}

	public JsonBaseElement getJsonElement(Element element) {
		switch (element) {
		case ARRAY:
			return new ArrayElement();
		case JSON:
			return new JsonElement();
		case STRING:
			return new StringElement();
		case NUMBER:
			return new NumberElement();
		case TRUE:
			return new TrueElement();
		case FALSE:
			return new FalseElement();
		case NULL:
			return new NullElement();
		default:
			break;
		}
		return null;
	}
}
