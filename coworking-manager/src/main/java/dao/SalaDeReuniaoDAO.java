package dao;

import java.util.List;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.SalaDeReuniao;

public class SalaDeReuniaoDAO extends DAO{
	public SalaDeReuniaoDAO() {
		super("SalasDeReuniao");
	}
public void salvarArray(SalaDeReuniao saladereuniao) {
	JsonArray array=lerArray();
	
	JsonObject obj=new JsonObject();
	 obj.addProperty("id", saladereuniao.getId());
     obj.addProperty("nome", saladereuniao.getNome());
     obj.addProperty("capacidade", saladereuniao.getCapacidade());
     obj.addProperty("disponivel", saladereuniao.isDisponivel());
     obj.addProperty("precoPorHora", saladereuniao.getPrecoPorHora());

     array.add(obj);
     salvarArray(array);
}    
public List<SalaDeReuniao> listarTodos(){
	
	List<SalaDeReuniao> lista=new ArrayList<>();
	
	JsonArray array = lerArray();
	
	for(var elemento : array) {
			
		JsonObject obj=elemento.getAsJsonObject();
		
		SalaDeReuniao s=new SalaDeReuniao(
				obj.get("id").getAsString(),
				obj.get("nome").getAsString(),
				obj.get("capacidade").getAsInt(),
				obj.get("disponivel").getAsBoolean(),
				obj.get("precoPorHora").getAsDouble(),
				obj.get("projetor").getAsBoolean()
				);
		
		lista.add(s);
	}
	
	return lista;
		
	
	}
}