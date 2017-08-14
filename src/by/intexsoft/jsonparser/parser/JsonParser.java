package by.intexsoft.jsonparser.parser;

import java.util.List;

import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.element.JsonElement;
import by.intexsoft.jsonparser.element.StringElement;
import by.intexsoft.jsonparser.exception.NotValidJsonException;
import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.util.UtilityMethods;

/**
 * Класс, который парсит строку для Json начинающуюся с '{' и заканчивающуюся -
 * '}'. Наследуются от CompositeElementParser
 */
public class JsonParser extends CompositeElementParser {
	private static final Character COLON = ':';

	public JsonParser(String parsedElement) throws UnsupportedMethodException {
		super(parsedElement);
	}

	@Override
	public JsonBaseElement getJsonElement() throws NotValidJsonException, UnsupportedMethodException {
		JsonBaseElement json = super.getJsonElement();
		for (String element : parsedElements) {
			Pair keyValue = convert(element);
			((JsonElement) json).add((StringElement) getJsonElementFrom(keyValue.getKey()), getJsonElementFrom(keyValue.getValue()));
		}
		return json;
	}

	private Pair convert(String element) {
		List<Character> characters = UtilityMethods.convertToChars(element);
		int index = 0;
		for (; index < characters.size(); index++) {
			if (isQoute(characters, index))
				changeCharCounterForQuote();
			if (closedOpenedCharacterCounters.getOrDefault(QOUTE, 0) == 1)
				continue;
			if (characters.get(index) == COLON)
				break;
		}

		return new Pair(UtilityMethods.join(characters.subList(0, index)), UtilityMethods.join(characters.subList(index + 1, characters.size())));
	}

	/**
	 * Хранит пару String key: String value
	 */
	private static class Pair {
		private String key;
		private String value;

		public Pair(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getKey() {

			return key;
		}
	}
}
