package by.intexsoft.jsonparser.reader.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import by.intexsoft.jsonparser.reader.JsonReader;

public class JsonFileReader implements JsonReader{
	private StringBuilder raw;
	private BufferedReader bufferedReader;
	
	public JsonFileReader(String filePath) throws FileNotFoundException{
		raw = new StringBuilder();
		bufferedReader = new BufferedReader(new FileReader(filePath));
	}
	
	private void readFile() throws IOException{
		String line;
		while ((line = bufferedReader.readLine()) != null){
			raw.append(line);
		}
		this.close();
	}
	
	/* (non-Javadoc)
	 * @see by.intexsoft.jsonparser.reader.JsonReader#getResult()
	 */
	@Override
	public String getResult() throws IOException {
		readFile();
		return raw.toString();
	}
	
	private void close() throws IOException{
		bufferedReader.close();
	}
}
