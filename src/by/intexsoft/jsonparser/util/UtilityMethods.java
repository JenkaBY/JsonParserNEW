package by.intexsoft.jsonparser.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Класс содержащий различные вспомогательные методы неотносящиеся к каким либо
 * другим методамю
 *
 */
public class UtilityMethods {
	/**
	 * Объединяет список, переданных значений в строку с помощью StringBuilder
	 * 
	 * @param objects
	 * @return String все соединенные элементы списка
	 */
	public static <T> String join(List<T> objects) {
		StringBuilder sb = new StringBuilder();
		objects.forEach(ch -> sb.append(ch));
		return sb.toString().trim();
	}

	/**
	 * Убирает переносы строк (\r, \n). Не учитывает такие же символы,
	 * находящиеся в строковых литералах.
	 * 
	 * @param text
	 *            текст из которого должны быть убраны символы
	 * @return строку без символов
	 */
	public static String removeExtraCharacters(String text) {
		return text.replaceAll("\n", "").replaceAll("\r", "").trim();
	}

	/**
	 * Возвращает первый символ из переданного списка.
	 * 
	 * @param chars
	 *            символов, из которых необходимо получить первый символ
	 * @return первый символ из переданного списка
	 */
	public static Character getFirstCharacterFrom(Set<Character> chars) {
		return chars.iterator().next();
	}

	/**
	 * Метод склеивает переданный список с помощью запятых. Пример{'a', 'b',
	 * 'c'} -> "a,b,c"
	 * 
	 * @param array
	 *            список объектов которые требуется объединить с помощью
	 *            запятыми
	 * @return String строка объектов, разделенных запятыми
	 */
	public static <T> String joinByComma(List<T> array) {
		return array.stream().map(Object::toString).collect(Collectors.joining(","));
	}

	/**
	 * Конвертирует в список символов переданную строку
	 * 
	 * @param object
	 *            строка которую требуются конвертировать
	 * @return List<Character> Список символов
	 */
	public static List<Character> convertToChars(String object) {
		return object.trim().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
	}
}
