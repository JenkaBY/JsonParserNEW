package by.intexsoft.jsonparser.parser.element;

import by.intexsoft.jsonparser.element.FalseElement;
import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.exception.NotValidJsonException;
import by.intexsoft.jsonparser.exception.UnsupportedMethodException;

public class FalseParser extends BaseParser {

	public FalseParser(String parsedElement) {
		super(parsedElement);
	}

	/**
	 * @return JsonBaseElement Возвращает FalseElement
	 * @throws UnsupportedMethodException 
	 */
	@Override
	public JsonBaseElement getJsonElement() throws NotValidJsonException, UnsupportedMethodException {
		super.validate();
		JsonBaseElement jsonElement = new FalseElement();
		jsonElement.setValue(getParsedElement());
		return jsonElement;
	}
}