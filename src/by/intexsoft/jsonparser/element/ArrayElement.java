package by.intexsoft.jsonparser.element;

import java.util.List;

import by.intexsoft.jsonparser.util.UtilityMethods;

public class ArrayElement extends JsonBaseElement {
	
	@SuppressWarnings("unchecked")
	public void add(JsonBaseElement json){
		((List<JsonBaseElement>) value).add(json);
	}
	
	@Override
	public void setValue(Object value){
	}
	
	@Override
	public boolean isArray(){
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString(){
		return "[" + UtilityMethods.joinByComma(((List<JsonBaseElement>) value)) + "]";
	}
}
