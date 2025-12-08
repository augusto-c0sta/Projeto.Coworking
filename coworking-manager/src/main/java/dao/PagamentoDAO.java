package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.Pagamento;

public class PagamentoDAO extends DAO {

    public PagamentoDAO() {
        super("Pagamentos");
    }

    public void salvarPagamento(Pagamento p) {
        JsonArray array = lerArray();
        JsonObject obj = new JsonObject();
        obj.addProperty("idPagamento", p.getIdPagamento());
        obj.addProperty("idReserva", p.getIdReserva());
        obj.addProperty("valorPago", p.getValorPago());
        obj.addProperty("data", p.getData().toString());
        obj.addProperty("metodo", p.getMetodo());
        array.add(obj);
        salvarArray(array);
    }

    public List<Pagamento> listarTodos() {
        List<Pagamento> lista = new ArrayList<>();
        JsonArray array = lerArray();
        for (var elemento : array) {
            JsonObject obj = elemento.getAsJsonObject();
            Pagamento p = new Pagamento(
                obj.get("idPagamento").getAsString(),
                obj.get("idReserva").getAsString(),
                obj.get("valorPago").getAsDouble(),
                LocalDateTime.parse(obj.get("data").getAsString()),
                obj.get("metodo").getAsString()
            );
            lista.add(p);
        }
        return lista;
    }
}