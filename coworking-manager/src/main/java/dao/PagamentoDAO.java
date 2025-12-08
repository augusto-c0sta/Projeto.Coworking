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
        obj.addProperty("dataPagamento", p.getDataPagamento().toString());
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
                    LocalDateTime.parse(obj.get("dataPagamento").getAsString()),
                    obj.get("metodo").getAsString()
            );

            lista.add(p);
        }

        return lista;
    }

    public Pagamento buscarPorId(String id) {

        JsonArray array = lerArray();

        for (var elemento : array) {

            JsonObject obj = elemento.getAsJsonObject();

            if (obj.get("idPagamento").getAsString().equals(id)) {

                return new Pagamento(
                        obj.get("idPagamento").getAsString(),
                        obj.get("idReserva").getAsString(),
                        obj.get("valorPago").getAsDouble(),
                        LocalDateTime.parse(obj.get("dataPagamento").getAsString()),
                        obj.get("metodo").getAsString()
                );
            }
        }

        return null;
    }
}