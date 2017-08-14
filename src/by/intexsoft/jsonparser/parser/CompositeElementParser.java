package by.intexsoft.jsonparser.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.exception.NotValidJsonException;
import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.factory.JsonElementFactory;
import by.intexsoft.jsonparser.factory.ParserFactory;
import by.intexsoft.jsonparser.parser.validator.helper.Element;
import by.intexsoft.jsonparser.util.UtilityMethods;

public abstract class CompositeElementParser extends BaseParser {
	protected Character spliterator = ',';
	protected List<String> parsedElements;
	// Счетчик открывающихся и закрывающихся скобок и кавычек.
	protected Map<Character, Integer> closedOpenedCharacterCounters;
	protected static List<Character> openedClosedChars;
	protected boolean wasSplitted;
	protected boolean wasParsed;

	// заполняет список следующими символами [ '[', ']', '{', '}' ]
	static {
		try {
			openedClosedChars = Arrays.asList(new Character[] { Element.ARRAY.getFirstCharacter(), Element.ARRAY.getLastCharacter(),
					Element.JSON.getFirstCharacter(), Element.JSON.getLastCharacter() });
		} catch (UnsupportedMethodException e) {
		}
	}

	public CompositeElementParser(String parsedElement) throws UnsupportedMethodException {
		super(parsedElement);
		parsedElements = new LinkedList<>();
		closedOpenedCharacterCounters = new HashMap<>();
	}

	/**
	 * Парсит переданную строкую. Создается новый BaseParser. Используется для почти рекурсивного парсинга
	 * 
	 * @param parsedElement строка которую необходимо распарсить
	 * @return JsonBaseElement 
	 * @throws NotValidJsonException Если имеются недопустимые символы. 
	 * @throws UnsupportedMethodException Если метод вызывается на недопустимых объектах.
	 */
	protected JsonBaseElement getJsonElementFrom(String parsedElement) throws NotValidJsonException, UnsupportedMethodException {
		BaseParser parser = ParserFactory.getInstance().getParser(UtilityMethods.removeExtraCharacters(parsedElement));
		return parser.getJsonElement();
	}

	@Override
	public JsonBaseElement getJsonElement() throws NotValidJsonException, UnsupportedMethodException {
		if (!wasSplitted)
			splitParsedElements();
		JsonBaseElement json = JsonElementFactory.getInstance().getJsonElement(validator.getElement());
		return json;
	}

	/**
	 * Всегда выбрасывается исключение UnsupportedMethodException. Не
	 * поддерживается данным классом и не должен поддерживаться наследниками.
	 * Т.к. это составной объект, который требуется еще разделить на
	 * составляющие.
	 * 
	 * @return Часть строки, которая должна быть преобразована в JSON элемент
	 * @throws NotValidJsonException
	 * @throws UnsupportedMethodException
	 */
	@Override
	protected String getParsedElement() throws UnsupportedMethodException {
		throw new UnsupportedMethodException();
	}

	/**
	 * Разделяет анализируемую строку - список символов на отдельные элементы.
	 * Разделяет используя spliterator - ','. РАзделенные подстроки хранятся в
	 * List<String> parsedElements
	 * 
	 * @throws UnsupportedMethodException
	 *             Если каким то образом будет применяться не для составных
	 *             объектов.
	 */
	protected void splitParsedElements() throws UnsupportedMethodException {
		int startIndex = getInitialPosition() + 1;
		for (currentPosition = startIndex; currentPosition <= getPositionLastCharacter(); currentPosition++) {
			if (isQoute())
				changeCharCounterForQuote();
			if (closedOpenedCharacterCounters.getOrDefault(QOUTE, 0) == 1)
				continue;

			checkClosedOpenedCharAndAddToOpenedClosedChars(parsedString.get(currentPosition));

			if ((parsedString.get(currentPosition) == spliterator && isAbleToSplit()) || isLastElement()) {
				// For debug only
				// System.out.println(UtilityMethods.join(parsedString.subList(startIndex,
				// currentPosition)));
				parsedElements.add(UtilityMethods.join(parsedString.subList(startIndex, currentPosition)));
				startIndex = currentPosition + 1;
			}
		}
		wasSplitted = true;
	}

	private boolean isLastElement() {
		return currentPosition == getPositionLastCharacter();
	}

	private int getPositionLastCharacter() {
		return parsedString.size() - 1;
	}

	private void incrementCharCounter(Character ch) {
		closedOpenedCharacterCounters.put(ch, closedOpenedCharacterCounters.getOrDefault(ch, 0) + 1);
	}

	private void decrementCharCounter(Character ch) {
		closedOpenedCharacterCounters.put(ch, closedOpenedCharacterCounters.getOrDefault(ch, 0) - 1);
	}

	protected void changeCharCounterForQuote() {
		if (closedOpenedCharacterCounters.getOrDefault(QOUTE, 0) == 0) {
			incrementCharCounter(QOUTE);
		} else {
			decrementCharCounter(QOUTE);
		}
	}

	private boolean isAbleToSplit() throws UnsupportedMethodException {
		return closedOpenedCharacterCounters.get(Element.ARRAY.getFirstCharacter()) == closedOpenedCharacterCounters
				.get(Element.ARRAY.getLastCharacter())
				&& closedOpenedCharacterCounters.get(Element.JSON.getFirstCharacter()) == closedOpenedCharacterCounters
						.get(Element.JSON.getLastCharacter());

	}

	private void checkClosedOpenedCharAndAddToOpenedClosedChars(Character ch) {
		if (openedClosedChars.contains(ch)) {
			incrementCharCounter(ch);
		}
	}

	@Override
	public boolean isCompositeParser() {
		return true;
	}
}
