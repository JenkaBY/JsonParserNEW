package by.intexsoft.jsonparser.factory;

import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.parser.element.ArrayParser;
import by.intexsoft.jsonparser.parser.element.BaseParser;
import by.intexsoft.jsonparser.parser.element.FalseParser;
import by.intexsoft.jsonparser.parser.element.JsonParser;
import by.intexsoft.jsonparser.parser.element.NullParser;
import by.intexsoft.jsonparser.parser.element.NumberParser;
import by.intexsoft.jsonparser.parser.element.StringParser;
import by.intexsoft.jsonparser.parser.element.TrueParser;
import by.intexsoft.jsonparser.parser.validator.helper.Element;

public class ParserFactory {
private static ParserFactory factory;
	
	private ParserFactory(){
	}

	public static ParserFactory getInstance() {
		if (factory == null){
			factory = new ParserFactory();
		}
		return factory;
	}
	
	public BaseParser getParser(String parsedElement) throws UnsupportedMethodException {
		Character character = parsedElement.toLowerCase().charAt(0);
		Element element = Element.getByFirst(character);
		switch (element) {
		case ARRAY:
			return new ArrayParser(parsedElement);
		case JSON:
			return new JsonParser(parsedElement);
		case STRING:
			return new StringParser(parsedElement);
		case NUMBER:
			return new NumberParser(parsedElement);
		case TRUE:
			return new TrueParser(parsedElement);
		case FALSE:
			return new FalseParser(parsedElement);
		case NULL:
			return new NullParser(parsedElement);
		default:
			break;
		}
		return null;
	}
}
