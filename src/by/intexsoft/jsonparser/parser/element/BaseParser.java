/**
 * 
 */
package by.intexsoft.jsonparser.parser.element;

import java.util.List;
import java.util.stream.Collectors;

import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.exception.NotValidJsonException;
import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.factory.JsonElementFactory;
import by.intexsoft.jsonparser.factory.ValidatorFactory;
import by.intexsoft.jsonparser.parser.validator.IValidator;
import by.intexsoft.jsonparser.parser.validator.helper.Element;
import by.intexsoft.jsonparser.util.UtilityMethods;

/**
 * @author JenkaBY
 *
 */
public abstract class BaseParser {
	protected int currentPosition;
	protected String restPart;
	protected String parsedElement;
	protected IValidator validator;
	protected List<Character> chars;
	protected static final Character ESCAPE = '\\';
	protected static final Character SPACE = ' ';
	protected static final Character QOUTE = '"';
	protected boolean wasParsed;
	protected int initialPosition;

	/**
	 * @param parsedElement
	 */
	public BaseParser(String parsedElement) {
		parsedElement = UtilityMethods.removeExtraCharacters(parsedElement);
		chars = parsedElement.trim().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
		validator = ValidatorFactory.getInstance().getValidator(parsedElement);
	}

	public JsonBaseElement getJsonElement() throws NotValidJsonException, UnsupportedMethodException {
		JsonBaseElement json = JsonElementFactory.getInstance().getJsonElement(validator.getElement());
		json.setValue(getParsedElement());
		return json;
	}

	/**
	 *
	 * @throws NotValidJsonException
	 * @throws UnsupportedMethodException
	 */
	protected void validate() throws NotValidJsonException, UnsupportedMethodException {
		if (validator.isValid(parsedElement)) {
			throw new NotValidJsonException();
		}
	}

	/**
	 * TODO Подумать, надо ли возвращать??? может стоит убрать??
	 * Определяет первую подстроку, содержащую строковый Json элемент. Остальную
	 * часть сохраняет в поле {@value <a>restPart</a>} Базовая реализация
	 * реализована для Null, True, False элементов. Для остальных элементов
	 * метод должен быть переопределен.
	 * 
	 * @return Часть строки, которая должна быть преобразована в JSON элемент
	 * @throws NotValidJsonException
	 * @throws UnsupportedMethodException
	 */
	public String getParsedElement() throws NotValidJsonException, UnsupportedMethodException {
		if (wasParsed)
			return parsedElement;
		Element element = validator.getElement();
		currentPosition = element.getPositionLastCharacter();
		if (element.getLastPossibleCharacters().contains(chars.get(currentPosition))) {
			setRestPart();
			setParsedElement();
		}
		return parsedElement;
	}

	/**
	 * TODO Подумать, надо ли возвращать??? может стоит убрать??
	 * @return String часть переданной в конструктор строки, за исключением
	 *         распарсенной подстроки, содержащей JsonElement
	 */
	protected String getNotParsedPart() {
		return restPart;
	}

	/**
	 * Определяет является ли предыдущий символ относительно currentPosition в
	 * массиве chars экранирующим символом - "\"
	 * 
	 * @return boolean true, если предыдущий символ относительно currentPosition
	 *         экранирующим.
	 */
	protected boolean isPreviousCharEscape() {
//		return chars.get(currentPosition - 1).equals(ESCAPE);
		return isPreviousCharEscape(chars, currentPosition);
	}

	protected boolean isPreviousCharEscape(List<Character> characters, int currentPosition) {
		return currentPosition == 0 ? false : characters.get(currentPosition - 1).equals(ESCAPE);
	}
	
	protected boolean isLastPossibleCharacter(Character character) {
		return validator.getElement().getLastPossibleCharacters().contains(character);
	}

	protected int getInitialPosition() {
		return initialPosition;
	}

	protected void setParsedElement() {
		parsedElement = chars.size() == (currentPosition + 1) ? UtilityMethods.join(chars)
				: UtilityMethods.join(chars.subList(getInitialPosition(), currentPosition + 1));
		wasParsed = true;
	}

	protected void setRestPart() {
		restPart = UtilityMethods.join(chars.subList(currentPosition, chars.size()));
	}

	public boolean isCompositeParser(){
		return false;
	}

	protected boolean isQoute(List<Character> characters, int position){
		return characters.get(position) == QOUTE && !isPreviousCharEscape(characters, position);
	}
	
	protected boolean isQoute() {
		return isQoute(chars, currentPosition);
	}
}
