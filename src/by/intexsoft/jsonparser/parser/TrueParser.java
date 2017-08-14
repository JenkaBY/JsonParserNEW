package by.intexsoft.jsonparser.parser;

import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.element.TrueElement;
import by.intexsoft.jsonparser.exception.NotValidJsonException;
import by.intexsoft.jsonparser.exception.UnsupportedMethodException;

public class TrueParser extends BaseParser {

	public TrueParser(String parsedElement) {
		super(parsedElement);
	}

	/**
	 * @return JsonBaseElement Возвращает TrueElement
	 * @throws UnsupportedMethodException 
	 */
	@Override
	public JsonBaseElement getJsonElement() throws NotValidJsonException, UnsupportedMethodException {
		super.validate();
		JsonBaseElement jsonElement = new TrueElement();
		jsonElement.setValue(getParsedElement());
		return jsonElement;
	}
}