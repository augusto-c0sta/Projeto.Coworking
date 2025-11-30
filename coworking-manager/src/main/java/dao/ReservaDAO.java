package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dao.EspacoDAO;

import model.Espaco;
import model.Reserva;

public class ReservaDAO extends DAO {

    public ReservaDAO() {
        super("Reservas"); // define o arquivo certo
    }

    public void salvarReserva(Reserva reserva) {

        JsonArray array = lerArray();
        JsonObject obj = new JsonObject();

        obj.addProperty("idReserva", reserva.getIdReserva());
        obj.addProperty("idEspaco", reserva.getEspaco().getId());
        obj.addProperty("inicio", reserva.getInicio().toString());
        obj.addProperty("fim", reserva.getFim().toString());
        obj.addProperty("valorTotal", reserva.getValorTotal());

        array.add(obj);
        salvarArray(array);
    }

    public List<Reserva> listarTodos() {

        List<Reserva> lista = new ArrayList<>();
        JsonArray array = lerArray();

        EspacoDAO espacoDAO = new EspacoDAO();  // ← cria os arquivos do Reservas.json

        for (var elemento : array) {

            JsonObject obj = elemento.getAsJsonObject();

            String idReserva = obj.get("idReserva").getAsString();
            String idEspaco = obj.get("idEspaco").getAsString();
            LocalDateTime inicio = LocalDateTime.parse(obj.get("inicio").getAsString());
            LocalDateTime fim = LocalDateTime.parse(obj.get("fim").getAsString());
            double total = obj.get("valorTotal").getAsDouble();
            Espaco espaco = espacoDAO.buscarPorId(idEspaco);
            Reserva r = new Reserva(idReserva, espaco, inicio, fim);
            r.setValorTotal(total); // total já calculado no JSON

            lista.add(r);
        }

        return lista;
    }

    // -----------------------
    // BUSCAR ESPAÇO POR ID
    // -----------------------
    public Espaco buscarEspacoParaReserva(String idEspaco) {

        EspacoDAO espacoDAO = new EspacoDAO();
        return espacoDAO.buscarPorId(idEspaco); // retorna ou null
    }
}