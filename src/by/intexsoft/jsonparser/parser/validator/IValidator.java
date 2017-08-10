package by.intexsoft.jsonparser.parser.validator;

import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.parser.validator.helper.Element;

public interface IValidator {

	default boolean isValid(String parsedElement) throws UnsupportedMethodException{
		return parsedElement != null;
	}
	
	public Element getElement();
}