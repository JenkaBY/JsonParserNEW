package by.intexsoft.jsonparser.element;

import java.util.List;

import by.intexsoft.jsonparser.util.UtilityMethods;

/**
 * @author JenkaBY
 *
 */
public final class ArrayElement extends JsonBaseElement {
	
	/**
	 * Добавляет в список переданный JsonBaseElement.
	 */
	@SuppressWarnings("unchecked")
	public void add(JsonBaseElement json){
		((List<JsonBaseElement>) getValue()).add(json);
	}
	
	@Override
	public boolean isArray(){
		return true;
	}
	
	@Override
	public String toString(){
		return "[" + UtilityMethods.joinByComma(getList()) + "]";
	}
	
	/**
	 * Получает JsonBaseElement элемент по номеру индекса
	 * @param index элемента массива, который требуется получить
	 * @return JsonBaseElement соответсвтующий индексу в списке
	 */
	public JsonBaseElement getBy(int index) {
		return getList().get(index);
	}
	
	/**
	 * Метод служит для получения размера списка JsonElement
	 * @return 
	 */
	public int size() {
		return getList().size();
	}
	
	@SuppressWarnings("unchecked")
	private List<JsonBaseElement> getList(){
		return (List<JsonBaseElement>) value;
	}
}
