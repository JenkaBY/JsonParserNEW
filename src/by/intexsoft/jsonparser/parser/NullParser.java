package by.intexsoft.jsonparser.parser;

import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.element.NullElement;
import by.intexsoft.jsonparser.exception.NotValidJsonException;
import by.intexsoft.jsonparser.exception.UnsupportedMethodException;

public class NullParser extends BaseParser {

	public NullParser(String parsedElement) {
		super(parsedElement);
	}

	/**
	 * @return JsonBaseElement Возвращает единичный NullElement
	 * @throws UnsupportedMethodException 
	 */
	@Override
	public JsonBaseElement getJsonElement() throws NotValidJsonException, UnsupportedMethodException {
		super.validate();
		JsonBaseElement jsonElement = new NullElement();
		jsonElement.setValue(getParsedElement());
		return jsonElement;
	}
}