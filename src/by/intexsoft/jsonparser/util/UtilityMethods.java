/**
 * 
 */
package by.intexsoft.jsonparser.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import by.intexsoft.jsonparser.exception.UnsupportedMethodException;

/**
 * @author JenkaBY
 *
 */
public class UtilityMethods {
/**
 * Соединяет список, переданных значений в строку с помощью StringBuilder
 * @param objects
 * @return String все соединенные элементы списка 
 */
	public static <T> String join(List<T> objects){
		StringBuilder sb = new StringBuilder();
		objects.forEach(ch -> sb.append(ch));
		return sb.toString().trim();
	}
	
	/**
	 * Убирает переносы строк (\r, \n). Не учитывает такие же символы, находящиеся в строковых литералах. 
	 * @param text текст из которого должны быть убраны символы
	 * @return строку без символов
	 */
	public static String removeExtraCharacters(String text) {
		return text.replaceAll("\n", "").replaceAll("\r", "").trim();
	}
	
	
	public static Character getFirstCharacterFrom(Set<Character> chars) throws UnsupportedMethodException {
		return chars.iterator().next();
	}
	
	public static <T> String split(List<T> array) {
		return array.stream()
		  .map(Object::toString)
		  .collect(Collectors.joining(","));
	}
	
	public static List<Character> convertToChars(String object){
		return object.trim().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
	}
}
