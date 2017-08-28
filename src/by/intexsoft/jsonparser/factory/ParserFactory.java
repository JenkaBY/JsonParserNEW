package by.intexsoft.jsonparser.factory;

import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.parser.ArrayParser;
import by.intexsoft.jsonparser.parser.BaseParser;
import by.intexsoft.jsonparser.parser.FalseParser;
import by.intexsoft.jsonparser.parser.JsonParser;
import by.intexsoft.jsonparser.parser.NullParser;
import by.intexsoft.jsonparser.parser.NumberParser;
import by.intexsoft.jsonparser.parser.StringParser;
import by.intexsoft.jsonparser.parser.TrueParser;
import by.intexsoft.jsonparser.parser.validator.helper.Element;
/**
 * Фабрика - синглтон, создающая BaseParser согласно типу переданной строке. 
 */
public final class ParserFactory {
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
			return null;
		}
	}
}
