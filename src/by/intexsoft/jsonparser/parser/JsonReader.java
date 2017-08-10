package by.intexsoft.jsonparser.parser;

import java.util.List;

import by.intexsoft.jsonparser.element.JsonBaseElement;
import by.intexsoft.jsonparser.util.UtilityMethods;

public class JsonReader {
	private List<JsonBaseElement> jsons;
	private String json;

	/**
	 * @param jsons
	 */
	public JsonReader(String json) {
		this.json = UtilityMethods.removeExtraCharacters(json);
	}

	public List<JsonBaseElement> getJSONs() {
		System.out.println(json);
		return jsons;
	}

	protected String removeExtraSymbols(String text) {
		return text.replaceAll("\n", "").replaceAll("\r", "").trim();
	}
}
