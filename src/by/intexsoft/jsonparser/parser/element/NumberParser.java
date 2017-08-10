package by.intexsoft.jsonparser.parser.element;

import by.intexsoft.jsonparser.exception.NotValidJsonException;

public class NumberParser extends BaseParser {

	public NumberParser(String parsedElement) {
		super(parsedElement);
	}

	/**
	 * 
	 * @return Часть строки, которая должна быть преобразована в JSON элемент
	 * @throws NotValidJsonException
	 */
	@Override
	public String getParsedElement() throws NotValidJsonException {
		if (wasParsed)
			return parsedElement;
		currentPosition = chars.contains(SPACE) ? chars.indexOf(SPACE) : chars.size() - 1;
		setRestPart();
		setParsedElement();
//		parsedElement = UtilityMethods.join(chars.subList(getInitialPosition(), currentPosition));
		return parsedElement;
	}

}
