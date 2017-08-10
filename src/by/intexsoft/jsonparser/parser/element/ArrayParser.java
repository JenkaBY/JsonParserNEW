package by.intexsoft.jsonparser.parser.element;

import by.intexsoft.jsonparser.element.ArrayElement;
import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.exception.NotValidJsonException;
import by.intexsoft.jsonparser.exception.UnsupportedMethodException;

public class ArrayParser extends CompositeElementParser {

	public ArrayParser(String parsedElement) throws UnsupportedMethodException {
		super(parsedElement);

	}

	@Override
	public JsonBaseElement getJsonElement() throws NotValidJsonException, UnsupportedMethodException {
		JsonBaseElement json = super.getJsonElement();
		for (String element : parsedElements) {
			((ArrayElement) json).add(getJsonElementFrom(element));
		}
		return json;
	}
}
