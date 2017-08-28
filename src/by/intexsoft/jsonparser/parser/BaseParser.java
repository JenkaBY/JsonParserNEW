package by.intexsoft.jsonparser.parser;

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
 * Абстрактный класс JSON парсера, содержит общие методы для всех реализаций
 * парсера({@link by.intexsoft.jsonparser.parser.ArrayParser},
 * {@link by.intexsoft.jsonparser.parser.FalseParser},
 * {@link by.intexsoft.jsonparser.parser.JsonParser},
 * {@link by.intexsoft.jsonparser.parser.NullParser},
 * {@link by.intexsoft.jsonparser.parser.NumberParser},
 * {@link by.intexsoft.jsonparser.parser.StringParser},
 * {@link by.intexsoft.jsonparser.parser.TrueParser}).
 * 
 */
public abstract class BaseParser {
	protected int currentPosition;
	// Часть строки, которая осталась не проанализированной
	protected String restPart;
	protected String parsedElement;
	protected IValidator validator;
	protected List<Character> parsedString;
	protected static final Character ESCAPE = '\\';
	protected static final Character SPACE = ' ';
	protected static final Character QOUTE = '"';
	protected boolean wasParsed;
	protected int initialPosition;

	/**
	 * В конструкторе убираются лишние пробелы, знаки переноса строка и т.д.
	 * parsedElement преобразовывается в список List<Character>, создается
	 * объект валидатора
	 * {@link by.intexsoft.jsonparser.parser.validator.IValidator}, служащего
	 * для проверки соответствия создаваемому объекту переданной строки
	 * 
	 * @param parsedElement
	 *            Входящая строка, которую необходимо проанализировать.
	 */
	public BaseParser(String parsedElement) {
		parsedElement = UtilityMethods.removeExtraCharacters(parsedElement);
		parsedString = parsedElement.trim().chars()
				.mapToObj(c -> (char) c)
				.collect(Collectors.toList());
		validator = ValidatorFactory.getInstance().getValidator(parsedElement);
	}

	
	/**
	 * Возвращает распарсенный JsonBaseElement объект. Тип элемента определяется {@link  by.intexsoft.jsonparser.factory.JsonElementFactory}
	 * @return JsonBaseElement распарсенный объект.
	 * @throws NotValidJsonException Если имеются недопустимые символы в анализируемых объектах. 
	 * @throws UnsupportedMethodException Если метод вызывается на недопустимых объектах. В основном пробрасывается наверх из других методов.
	 */
	public JsonBaseElement getJsonElement() throws NotValidJsonException, UnsupportedMethodException {
		JsonBaseElement json = JsonElementFactory.getInstance().getJsonElement(validator.getElement());
		json.setValue(getParsedElement());
		return json;
	}

	/**
	 * Производит проверку на соответствие переданной строки на наличие только
	 * разрешенных символов и знаков.
	 * 
	 * @throws NotValidJsonException
	 *             Если имеются недопустимые символы.
	 * @throws UnsupportedMethodException
	 *             Если метод вызывается на недопустимых объектах.
	 */
	protected void validate() throws NotValidJsonException, UnsupportedMethodException {
		if (validator.isValid(parsedElement)) {
			throw new NotValidJsonException();
		}
	}

	/**
	 * Определяет первую подстроку, содержащую строковый Json элемент. Остальную
	 * часть сохраняет в поле restPart. Базовая реализация только для
	 * {@link by.intexsoft.jsonparser.parser.TrueElement},
	 * {@link by.intexsoft.jsonparser.parser.NullElement},
	 * {@link by.intexsoft.jsonparser.parser.FalseElement} элементов. Для
	 * остальных элементов метод должен быть переопределен.
	 * 
	 * @return Часть строки, которая должна быть преобразована в JSON элемент
	 * @throws NotValidJsonException
	 * @throws UnsupportedMethodException
	 */
	protected String getParsedElement() throws NotValidJsonException, UnsupportedMethodException {
		if (wasParsed)
			return parsedElement;
		Element element = validator.getElement();
		currentPosition = element.getPositionLastCharacter();
		if (element.getLastPossibleCharacters().contains(parsedString.get(currentPosition))) {
			setRestPart();
			setParsedElement();
		}
		return parsedElement;
	}

	/**
	 * Определяет является ли предыдущий символ относительно currentPosition в
	 * массиве chars экранирующим символом - "\"
	 * 
	 * @return boolean true, если предыдущий символ относительно currentPosition
	 *         экранирующий.
	 */
	protected boolean isPreviousCharEscape() {
		return isPreviousCharEscape(parsedString, currentPosition);
	}

	/**
	 * Определяет является ли предыдущий символ относительно currentPosition в
	 * массиве chars экранирующим символом - "\"
	 * 
	 * @param characters
	 *            Список символов
	 * @param currentPosition
	 *            текущая позиция в переданном списке символов
	 * @return true, если предыдущий символ относительно currentPosition
	 *         экранирующий.
	 */
	protected boolean isPreviousCharEscape(List<Character> characters, int currentPosition) {
		return currentPosition == 0 ? false : characters.get(currentPosition - 1).equals(ESCAPE);
	}

	/**
	 * Определяет является ли переданный символ character символом конца
	 * анализируемого строки. Для строки это кавычки '"', для массива - ']' и
	 * т.д.
	 * 
	 * @param character
	 *            символ, который нужно проанализировать на
	 * @return boolean true если переданный символ является символом окончания
	 *         анализируемой строки.
	 */
	protected boolean isLastPossibleCharacter(Character character) {
		return validator.getElement().getLastPossibleCharacters().contains(character);
	}

	/*
	 * Возвращает начальную позицию, с которй должен начинаться анализ
	 * переданной строкию. Например для StringElement не должны учитываться
	 * кавычки, для ArrayElement - скобки и т.д
	 * 
	 * @return int Начальную позицию, с которой должен начинаться анализ
	 */
	protected int getInitialPosition() {
		return initialPosition;
	}

	/**
	 * Сохраняет проанализированный список символов в строку parsedElement и
	 * устанавливает переменную wasParsed в true. Проанализированный объект
	 * создается из элементов начинающихся с initialPosition до
	 * currentPosition+1. Учитывает пустые объекты ("", [], {}).
	 */
	protected void setParsedElement() {
		parsedElement = parsedString.size() == (currentPosition + 1) ? UtilityMethods.join(parsedString)
				: UtilityMethods.join(parsedString.subList(getInitialPosition(), currentPosition + 1));
		wasParsed = true;
	}

	/**
	 * Сохраняет часть подстроки, которая не подвергалась анализу.
	 */
	protected void setRestPart() {
		restPart = UtilityMethods.join(parsedString.subList(currentPosition, parsedString.size()));
	}

	/**
	 * Определяет стоит ли считать текущий символ в переданном списке символов
	 * characters в позиции position кавычкой. Если перед кавычкой стоит
	 * экранирующий символ, то кавычка не будет считаться кавычкой.
	 * 
	 * @param characters
	 *            Список переданных символов
	 * @param position
	 *            Позиция в списке для которой нужно определить
	 * @return boolean true если перед кавычкой нет экранирующего символа.
	 */
	protected boolean isQoute(List<Character> characters, int position) {
		return characters.get(position) == QOUTE && !isPreviousCharEscape(characters, position);
	}

	/**
	 * Определяет стоит ли считать текущий символ в переданном списке символов
	 * characters в позиции position кавычкой. Если перед кавычкой стоит
	 * экранирующий символ, то кавычка не будет считаться кавычкой.
	 * 
	 * @return boolean true если перед кавычкой нет экранирующего символа.
	 */
	protected boolean isQoute() {
		return isQoute(parsedString, currentPosition);
	}

	/**
	 * @return boolean true если текуцщий парсер - это парсер составных объектов:
	 *         {@link by.intexsoft.jsonparser.parser.ArrayParser},
	 *         {@link by.intexsoft.jsonparser.parser.JsonParser}
	 */
	public boolean isCompositeParser() {
		return false;
	}
}
