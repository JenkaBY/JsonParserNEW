package by.intexsoft.jsonparser.parser.validator.helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.util.UtilityMethods;

public enum Element {
	ARRAY, JSON, NUMBER, STRING, FALSE, TRUE, NULL;

	private static Set<Character> arrayStartsWith;
	private static Set<Character> arrayEndsWith;

	private static Set<Character> jsonStartsWith;
	private static Set<Character> jsonEndsWith;

	private static Set<Character> numberStartsWith;
	private static Set<Character> numberEndsWith;

	private static Set<Character> stringStartsOrEndsWith;
	private static Set<Character> falseStartsWith;
	private static Set<Character> trueStartsWith;
	private static Set<Character> nullStartsWith;
	private static Set<Character> falseEndsWith;
	private static Set<Character> trueEndsWith;
	private static Set<Character> nullEndsWith;

	static {
		arrayStartsWith = new HashSet<>(Arrays.asList('['));
		arrayEndsWith = new HashSet<>(Arrays.asList(']'));

		jsonStartsWith = new HashSet<>(Arrays.asList('{'));
		jsonEndsWith = new HashSet<>(Arrays.asList('}'));

		Set<Character> numbers = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
		numberStartsWith = new HashSet<>(numbers);
		numberStartsWith.add('-');
		numberEndsWith = numbers;

		stringStartsOrEndsWith = new HashSet<>(Arrays.asList('"'));

		falseStartsWith = new HashSet<>(Arrays.asList('f'));
		falseEndsWith = new HashSet<>(Arrays.asList('e'));

		trueStartsWith = new HashSet<>(Arrays.asList('t'));
		trueEndsWith = falseEndsWith;

		nullStartsWith = new HashSet<>(Arrays.asList('n'));
		nullEndsWith = new HashSet<>(Arrays.asList('l'));
	}

	public static Element getByFirst(Character character) {
		if (jsonStartsWith.contains(character))
			return JSON;
		if (arrayStartsWith.contains(character))
			return ARRAY;
		if (numberStartsWith.contains(character))
			return NUMBER;
		if (stringStartsOrEndsWith.contains(character))
			return STRING;
		if (falseStartsWith.contains(character))
			return FALSE;
		if (trueStartsWith.contains(character))
			return TRUE;
		if (nullStartsWith.contains(character))
			return NULL;
		return null;
	}

	public Set<Character> getFirstPossibleCharacters() {
		switch (this) {
		case JSON:
			return jsonStartsWith;
		case ARRAY:
			return arrayStartsWith;
		case NUMBER:
			return numberStartsWith;
		case STRING:
			return stringStartsOrEndsWith;
		case FALSE:
			return falseStartsWith;
		case TRUE:
			return trueStartsWith;
		case NULL:
			return nullStartsWith;
		default:
			break;
		}
		return null;
	}

	public Set<Character> getLastPossibleCharacters() {
		switch (this) {
		case JSON:
			return jsonEndsWith;
		case ARRAY:
			return arrayEndsWith;
		case NUMBER:
			return numberEndsWith;
		case STRING:
			return stringStartsOrEndsWith;
		case FALSE:
			return falseEndsWith;
		case TRUE:
			return trueEndsWith;
		case NULL:
			return nullEndsWith;
		default:
			break;
		}
		return null;
	}

	public int getMinPossibleLength() throws UnsupportedMethodException {
		switch (this) {
		case JSON:
		case STRING:
		case ARRAY:
			return 2;
		case FALSE:
		case TRUE:
		case NULL:
			return getPositionLastCharacter() + 1;
		default:
			return 1;
		}
	}

	/**
	 * Определяет позицию последнего элемента в его названии элемента. Счет начинается с 0. Например, метод для FALSE вернет 4, для NULL - 3
	 * 
	 * @return int позиция последнего элемента
	 * @throws UnsupportedMethodException
	 *             если метод вызывается у объектов отличных от FALSE, TRUE, NULL
	 */
	public int getPositionLastCharacter() throws UnsupportedMethodException {
		if (!Arrays.asList(new Element[] { FALSE, TRUE, NULL }).contains(this)) {
			throw new UnsupportedMethodException();
		}
		return name().length() - 1;
	}
	
	/**
	 * 
	 * @return int 
	 * @throws UnsupportedMethodException
	 *             если метод вызывается у объектов отличных от ARRAY, JSON
	 */
	public Character getLastCharacter() throws UnsupportedMethodException {
		return getFirstCharacterFrom(getLastPossibleCharacters());
	}

	/**
	 * 
	 * @return int 
	 * @throws UnsupportedMethodException
	 *             если метод вызывается у объектов отличных от ARRAY, JSON
	 */
	public Character getFirstCharacter() throws UnsupportedMethodException {
		return getFirstCharacterFrom(getFirstPossibleCharacters());
	}
	
	private Character getFirstCharacterFrom(Set<Character> chars) throws UnsupportedMethodException {
		if (!Arrays.asList(new Element[] { ARRAY, JSON, STRING }).contains(this)) {
			throw new UnsupportedMethodException();
		}
		return UtilityMethods.getFirstCharacterFrom(chars);
	}
}
