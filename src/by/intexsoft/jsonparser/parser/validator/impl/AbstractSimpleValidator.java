package by.intexsoft.jsonparser.parser.validator.impl;

import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.parser.validator.IValidator;
import by.intexsoft.jsonparser.parser.validator.helper.Element;


/**
 *	Абстрактный класс валидатора. Реализует базовую функциональность валитора для JsonElement.
 */
public abstract class AbstractSimpleValidator implements IValidator {
	private Element element;

	public AbstractSimpleValidator(Element element) {
		this.element = element;
	}

	/* 
	 * Проверяет валидная ли переданная строка. Проверяет первый, последний символ и то, что между ними
	 */
	@Override
	public boolean isValid(String parsedElement) throws UnsupportedMethodException {
		return IValidator.super.isValid(parsedElement) && startsWith(parsedElement) && endsWith(parsedElement) && validateLength(parsedElement)
				&& validateMiddle(parsedElement);
	}

	protected boolean validateMiddle(String parsedElement) {
		return true;
	}

	protected boolean startsWith(String parsedElement) {
		return element.getFirstPossibleCharacters().contains(getFirstCharacterInLowerCase(parsedElement));
	}

	protected boolean endsWith(String parsedElement) {
		return element.getFirstPossibleCharacters().contains(getLastCharacterInLowerCase(parsedElement));
	}

	protected boolean validateLength(String parsedElement) throws UnsupportedMethodException {
		return parsedElement.length() >= element.getMinPossibleLength();
	}

	protected Character getFirstCharacterInLowerCase(String parsedElement) {
		return parsedElement.charAt(0);
	}

	protected Character getLastCharacterInLowerCase(String parsedElement) {
		return parsedElement.charAt(parsedElement.length() - 1);
	}

	public Element getElement() {
		return element;
	}
}