package dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.Espaco;
import model.SalaDeReuniao;
import model.CabineIndividual;
import model.Auditorio;

public class EspacoDAO extends DAO {

    public EspacoDAO() {
        super("Espacos"); // tudo vai para "Espacos.json"
    }

    // -----------------------------------
    // SALVAR (polimórfico)
    // -----------------------------------
    public void salvar(Espaco espaco) {

        JsonArray array = lerArray();
        JsonObject obj = new JsonObject();

        // dados comuns
        obj.addProperty("id", espaco.getId());
        obj.addProperty("nome", espaco.getNome());
        obj.addProperty("capacidade", espaco.getCapacidade());
        obj.addProperty("disponivel", espaco.isDisponivel());
        obj.addProperty("precoPorHora", espaco.getPrecoPorHora());

        // dados específicos
        if (espaco instanceof SalaDeReuniao sala) {
            obj.addProperty("tipo", "SalaDeReuniao");
            obj.addProperty("projetor", sala.isUsoDoProjetor());
        }
        else if (espaco instanceof CabineIndividual) {
            obj.addProperty("tipo", "CabineIndividual");
        }
        else if (espaco instanceof Auditorio auditorio) {
            obj.addProperty("tipo", "Auditorio");
            obj.addProperty("evento", auditorio.isEvento());
        }

        // adiciona ao json
        array.add(obj);

        salvarArray(array);
    }

    // -----------------------------------
    // LISTAR TODOS (polimórfico)
    // -----------------------------------
    public List<Espaco> listarTodos() {

        List<Espaco> lista = new ArrayList<>();
        JsonArray array = lerArray();

        for (var elemento : array) {
            JsonObject obj = elemento.getAsJsonObject();
            String tipo = obj.get("tipo").getAsString();

            Espaco e = null;

            switch (tipo) {
                case "SalaDeReuniao":
                    e = new SalaDeReuniao(
                        obj.get("id").getAsString(),
                        obj.get("nome").getAsString(),
                        obj.get("capacidade").getAsInt(),
                        obj.get("disponivel").getAsBoolean(),
                        obj.get("precoPorHora").getAsDouble(),
                        obj.get("projetor").getAsBoolean()
                    );
                    break;

                case "CabineIndividual":
                    e = new CabineIndividual(
                        obj.get("id").getAsString(),
                        obj.get("nome").getAsString(),
                        obj.get("capacidade").getAsInt(),
                        obj.get("disponivel").getAsBoolean(),
                        obj.get("precoPorHora").getAsDouble()
                    );
                    break;

                case "Auditorio":
                    e = new Auditorio(
                        obj.get("id").getAsString(),
                        obj.get("nome").getAsString(),
                        obj.get("capacidade").getAsInt(),
                        obj.get("disponivel").getAsBoolean(),
                        obj.get("precoPorHora").getAsDouble(),
                        obj.get("evento").getAsBoolean()
                    );
                    break;
            }

            lista.add(e);
        }

        return lista;
    }
    
    private boolean getBooleanSafe(JsonObject obj, String campo) {
        return obj.has(campo) && !obj.get(campo).isJsonNull()
               ? obj.get(campo).getAsBoolean()
               : false;
    }
    
    public Espaco buscarPorId(String id) {

        JsonArray array = lerArray();

        for (var elemento : array) {

            JsonObject obj = elemento.getAsJsonObject();

            if (!obj.get("id").getAsString().equals(id)) continue;

            String tipo = obj.get("tipo").getAsString();

            switch (tipo) {

                case "SalaDeReuniao":
                    return new SalaDeReuniao(
                        obj.get("id").getAsString(),
                        obj.get("nome").getAsString(),
                        obj.get("capacidade").getAsInt(),
                        obj.get("disponivel").getAsBoolean(),
                        obj.get("precoPorHora").getAsDouble(),
                        getBooleanSafe(obj, "projetor")
                    );

                case "CabineIndividual":
                    return new CabineIndividual(
                        obj.get("id").getAsString(),
                        obj.get("nome").getAsString(),
                        obj.get("capacidade").getAsInt(),
                        obj.get("disponivel").getAsBoolean(),
                        obj.get("precoPorHora").getAsDouble()
                    );

                case "Auditorio":
                    return new Auditorio(
                        obj.get("id").getAsString(),
                        obj.get("nome").getAsString(),
                        obj.get("capacidade").getAsInt(),
                        obj.get("disponivel").getAsBoolean(),
                        obj.get("precoPorHora").getAsDouble(),
                        getBooleanSafe(obj, "evento")
                    );
            }
        }

        return null;
    }
}