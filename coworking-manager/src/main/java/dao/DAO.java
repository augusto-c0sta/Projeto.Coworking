package dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class DAO {
	
	protected String TIPO;
	
	protected Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public DAO(String tipo) {
		this.TIPO= "data/" + tipo + ".json";
	}
	protected JsonArray lerArray() {
		try(FileReader leitor= new FileReader(TIPO)){
			JsonElement elemento=JsonParser.parseReader(leitor);
			if(elemento !=null && elemento.isJsonArray()) {
				return elemento.getAsJsonArray();
			}
		}catch (IOException e) {
			System.out.println("Erro ao ler arquivo Json" + TIPO);
		}
		
		return new JsonArray();
	}
	protected void salvarArray(JsonArray array) {
		try (FileWriter escritor = new FileWriter(TIPO)){
			GSON.toJson(array, escritor);
		}catch(IOException e) {
			System.out.println("Erro ao salvar array no Json: " + TIPO);
		}

	}

}
