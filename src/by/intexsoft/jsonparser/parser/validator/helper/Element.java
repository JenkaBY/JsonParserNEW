package by.intexsoft.jsonparser.parser.validator.helper;

import static java.util.Collections.singletonList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.util.UtilityMethods;

/**
 * Перечисление которое определяет тип элемента. Содержит некоторые методы,
 * позволяющие первый и последний возможные символы, минимально допустимую длину
 * элемента
 */
public enum Element {
	ARRAY, JSON, NUMBER, STRING, FALSE, TRUE, NULL;

	private final static Set<Character> ARRAY_STARTS_WITH;
	private final static Set<Character> ARRAY_ENDS_WITH;

	private final static Set<Character> JSON_STARTS_WITH;
	private final static Set<Character> JSON_ENDS_WITH;

	private final static Set<Character> NUMBER_STARTS_WITH;
	private final static Set<Character> NUMBER_ENDS_WITH;

	private final static Set<Character> STRING_STARTS_OR_ENDS_WITH;
	private final static Set<Character> FALSE_STARTS_WITH;
	private final static Set<Character> TRUE_STARTS_WITH;
	private final static Set<Character> NULL_STARTS_WITH;
	private final static Set<Character> FALSE_ENDS_WITH;
	private final static Set<Character> TRUE_ENDS_WITH;
	private final static Set<Character> NULL_ENDS_WITH;

	static {
		ARRAY_STARTS_WITH = new HashSet<>(singletonList('['));
		ARRAY_ENDS_WITH = new HashSet<>(singletonList(']'));

		JSON_STARTS_WITH = new HashSet<>(singletonList('{'));
		JSON_ENDS_WITH = new HashSet<>(singletonList('}'));

		final List<Character> chars = IntStream.range(0, 10)
				.boxed()
				.map(i -> new Character(String.valueOf(i).charAt(0)))
				.collect(Collectors.toList());
		Set<Character> numbers = new HashSet<>(chars);
		NUMBER_STARTS_WITH = new HashSet<>(numbers);
		NUMBER_STARTS_WITH.add('-');
		NUMBER_ENDS_WITH = numbers;

		STRING_STARTS_OR_ENDS_WITH = new HashSet<>(singletonList('"'));

		FALSE_STARTS_WITH = new HashSet<>(singletonList('f'));
		FALSE_ENDS_WITH = new HashSet<>(singletonList('e'));

		TRUE_STARTS_WITH = new HashSet<>(singletonList('t'));
		TRUE_ENDS_WITH = FALSE_ENDS_WITH;

		NULL_STARTS_WITH = new HashSet<>(singletonList('n'));
		NULL_ENDS_WITH = new HashSet<>(singletonList('l'));
	}

	/**
	 * Определяет элемент переданному символу
	 * 
	 * @param character
	 *            символ по которому происходит определение Element
	 * @return Element
	 */
	public static Element getByFirst(Character character) {
		if (JSON_STARTS_WITH.contains(character))
			return JSON;
		if (ARRAY_STARTS_WITH.contains(character))
			return ARRAY;
		if (NUMBER_STARTS_WITH.contains(character))
			return NUMBER;
		if (STRING_STARTS_OR_ENDS_WITH.contains(character))
			return STRING;
		if (FALSE_STARTS_WITH.contains(character))
			return FALSE;
		if (TRUE_STARTS_WITH.contains(character))
			return TRUE;
		if (NULL_STARTS_WITH.contains(character))
			return NULL;
		return null;
	}

	/**
	 * Возвращает набор первых возможных символов для конкретного элемента
	 * 
	 * @return Set<Character> набор первых возможных символов для конкретного
	 *         элемента
	 */
	public Set<Character> getFirstPossibleCharacters() {
		switch (this) {
		case JSON:
			return JSON_STARTS_WITH;
		case ARRAY:
			return ARRAY_STARTS_WITH;
		case NUMBER:
			return NUMBER_STARTS_WITH;
		case STRING:
			return STRING_STARTS_OR_ENDS_WITH;
		case FALSE:
			return FALSE_STARTS_WITH;
		case TRUE:
			return TRUE_STARTS_WITH;
		case NULL:
			return NULL_STARTS_WITH;
		default:
			return null;
		}
	}

	/**
	 * Возвращает набор последних возможных символов для конкретного элемента
	 * 
	 * @return Set<Character> набор последних возможных символов для конкретного
	 *         элемента
	 */
	public Set<Character> getLastPossibleCharacters() {
		switch (this) {
		case JSON:
			return JSON_ENDS_WITH;
		case ARRAY:
			return ARRAY_ENDS_WITH;
		case NUMBER:
			return NUMBER_ENDS_WITH;
		case STRING:
			return STRING_STARTS_OR_ENDS_WITH;
		case FALSE:
			return FALSE_ENDS_WITH;
		case TRUE:
			return TRUE_ENDS_WITH;
		case NULL:
			return NULL_ENDS_WITH;
		default:
			break;
		}
		return null;
	}

	/**
	 * Определяет минимальную возможную длину JsonObject для конкретного элемента
	 * 
	 * @return int минимальную возможную длину
	 */
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
	 * Определяет позицию последнего элемента в его названии элемента. Счет
	 * начинается с 0. Например, метод для FALSE вернет 4, для NULL - 3
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
	 * @return Character последний символ у строки содержащей ARRAY или JSON
	 * @throws UnsupportedMethodException
	 *             если метод вызывается у объектов отличных от ARRAY, JSON
	 */
	public Character getLastCharacter() throws UnsupportedMethodException {
		return getFirstCharacterFrom(getLastPossibleCharacters());
	}

	/**
	 * 
	 * @return Character первый символ у строки содержащей ARRAY или JSON
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
