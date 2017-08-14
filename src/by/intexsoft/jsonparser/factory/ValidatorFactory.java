/**
 * 
 */
package by.intexsoft.jsonparser.factory;

import by.intexsoft.jsonparser.parser.validator.IValidator;
import by.intexsoft.jsonparser.parser.validator.helper.Element;
import by.intexsoft.jsonparser.parser.validator.impl.ArrayValidator;
import by.intexsoft.jsonparser.parser.validator.impl.FalseValidator;
import by.intexsoft.jsonparser.parser.validator.impl.JsonValidator;
import by.intexsoft.jsonparser.parser.validator.impl.NullValidator;
import by.intexsoft.jsonparser.parser.validator.impl.NumberValidator;
import by.intexsoft.jsonparser.parser.validator.impl.StringValidator;
import by.intexsoft.jsonparser.parser.validator.impl.TrueValidator;

/**
 * Фабрика - синглтон, создающая IValidator по согласно типу переданной строке. 
 */
public class ValidatorFactory {
	private static ValidatorFactory factory;
	
	private ValidatorFactory(){
	}

	public static ValidatorFactory getInstance() {
		if (factory == null){
			factory = new ValidatorFactory();
		}
		return factory;
	}
	
	public IValidator getValidator(String parsedElement) {
		Character character = parsedElement.toLowerCase().charAt(0);
		Element element = Element.getByFirst(character);
		switch (element) {
		case ARRAY:
			return new ArrayValidator(element);
		case JSON:
			return new JsonValidator(element);
		case STRING:
			return new StringValidator(element);
		case NUMBER:
			return new NumberValidator(element);
		case TRUE:
			return new TrueValidator(element);
		case FALSE:
			return new FalseValidator(element);
		case NULL:
			return new NullValidator(element);
		default:
			break;
		}
		return null;
	}
}
