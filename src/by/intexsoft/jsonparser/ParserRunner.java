package by.intexsoft.jsonparser;

import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.exception.NotValidJsonException;
import by.intexsoft.jsonparser.exception.UnsupportedMethodException;
import by.intexsoft.jsonparser.factory.ParserFactory;
import by.intexsoft.jsonparser.parser.BaseParser;
import by.intexsoft.jsonparser.reader.impl.JsonFileReader;
import by.intexsoft.jsonparser.util.UtilityMethods;

public class ParserRunner {

	public static void main(String[] args) throws Exception {
		String pathToFolder = ".\\example\\";
		String fullJson = "json.txt";
		String stringJson = "json-string.txt";
		String nullJson = "json-null.txt";
		String trueJson = "json-true.txt";
		String falseJson = "json-false.txt";
		String numberJson = "json-number.txt";
		String arrayJson = "json-array.txt";

		String string = new JsonFileReader(pathToFolder + stringJson).getResult();
		String nullJsonString = new JsonFileReader(pathToFolder + nullJson).getResult();
		String trueJsonString = new JsonFileReader(pathToFolder + trueJson).getResult();
		String falseJsonString = new JsonFileReader(pathToFolder + falseJson).getResult();
		String numberJsonString = new JsonFileReader(pathToFolder + numberJson).getResult();
		String arrayJsonString = new JsonFileReader(pathToFolder + arrayJson).getResult();
		String objectJsonString = new JsonFileReader(pathToFolder + fullJson).getResult();

		createJson(string);
		createJson(nullJsonString);
		createJson(trueJsonString);
		createJson(falseJsonString);
		createJson(numberJsonString);
		createJson(arrayJsonString);
		createJson(objectJsonString);

		System.out.println("End.");
	}

	private static void createJson(String json) throws NotValidJsonException, UnsupportedMethodException {
		print("From Reader: ", json);
		BaseParser parser = ParserFactory.getInstance().getParser(UtilityMethods.removeExtraCharacters(json));
		JsonBaseElement jsonElement = parser.getJsonElement();
		print(jsonElement.getClass() + " : ", jsonElement);
	}

	public static void printParsedElement(Object object) {
		print("Parsed string Element: ", object);
	}

	public static void printJSONElement(Object object) {
		print("Parsed JSON Element: ", object);
	}

	public static void print(String textBefore, Object message) {
		System.out.println(textBefore + message);
	}

}
