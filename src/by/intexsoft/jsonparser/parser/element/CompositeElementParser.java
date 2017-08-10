package by.intexsoft.jsonparser.parser.element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import by.intexsoft.jsonparser.element.ArrayElement;
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
	protected Map<Character, Integer> closedOpenedCharacterCounters;
	protected static List<Character> openedClosedChars;
	protected boolean wasSplitted;
	protected boolean wasParsed;

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
	
	
	// TODO REMOVE!!!
	// TODO Подумать, надо ли возвращать??? может стоит убрать??
	public List<String> getParsedElements() throws UnsupportedMethodException {
		splitParsedElements();
		return parsedElements;
	}

	/**
	 * // TODO REMOVE!!!  TODO Подумать, надо ли возвращать??? может стоит
	 * убрать?? Определяет первую подстроку, содержащую строковый Json элемент.
	 * Остальную часть сохраняет в поле {@value <a>restPart</a>} Базовая
	 * реализация реализована для Null, True, False элементов. Для остальных
	 * элементов метод должен быть переопределен.
	 * 
	 * @return Часть строки, которая должна быть преобразована в JSON элемент
	 * @throws NotValidJsonException
	 * @throws UnsupportedMethodException
	 */
	@Override
	public String getParsedElement() throws NotValidJsonException, UnsupportedMethodException {
		throw new UnsupportedMethodException();
	}

	protected void splitParsedElements() throws UnsupportedMethodException {
		int startIndex = getInitialPosition() + 1;
		for (currentPosition = startIndex; currentPosition <= getPositionLastCharecter(); currentPosition++) {
			if (isQoute())
				changeCharCounterForQuote();
			if (closedOpenedCharacterCounters.getOrDefault(QOUTE, 0) == 1)
				continue;

			checkClosedOpenedCharAndAddToOpenedClosedChars(chars.get(currentPosition));

			if ((chars.get(currentPosition) == spliterator && isAbleToSplit()) || isLastElement()) {
				 System.out.println(UtilityMethods.join(chars.subList(startIndex,
				 currentPosition)));
				parsedElements.add(UtilityMethods.join(chars.subList(startIndex, currentPosition)));
				startIndex = currentPosition + 1;
			}
		}
		wasSplitted = true;
	}

	private boolean isLastElement() {
		return currentPosition == getPositionLastCharecter();
	}

	private int getPositionLastCharecter() {
		return chars.size() - 1;
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
