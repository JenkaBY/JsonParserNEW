package by.intexsoft.jsonparser.parser;

import by.intexsoft.jsonparser.exception.NotValidJsonException;
import by.intexsoft.jsonparser.util.UtilityMethods;

public class StringParser extends BaseParser {

	public StringParser(String parsedElement) {
		super(parsedElement);
		initialPosition = 1;
	}

	/**
	 * @return JsonBaseElement Возвращает единичный StringElement
	 */
//	@Override
//	public JsonBaseElement getJsonElement() throws NotValidJsonException {
////		super.validate();
//		JsonBaseElement jsonElement = new StringElement();
//		jsonElement.setValue(getParsedElement());
//		return jsonElement;
//	}
	
	/**
	 * Определяет первую подстроку, содержащую строковый Json элемент. Остальную
	 * часть сохраняет в поле {@value <a>restPart</a>}
	 * 
	 * @return Часть строки, которая должна быть преобразована в JSON элемент
	 * @throws NotValidJsonException
	 */
	@Override
	public String getParsedElement() throws NotValidJsonException {
		if (wasParsed)
			return parsedElement;
		for (currentPosition = getInitialPosition(); currentPosition < parsedString.size(); currentPosition++) {
			if (isLastPossibleCharacter(parsedString.get(currentPosition)) && !isPreviousCharEscape()) {
				setRestPart();
				setParsedElement();
				break;
			}
		}
		return parsedElement;
	}
	
	@Override
	protected void setParsedElement() {
		parsedElement = UtilityMethods.join(parsedString.subList(initialPosition, currentPosition));
		wasParsed = true;
	}
}
