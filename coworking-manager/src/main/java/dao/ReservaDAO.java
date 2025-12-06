package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

    // ======================================================
    // LISTAR TODAS AS RESERVAS
    // ======================================================
    public List<Reserva> listarTodos(EspacoDAO espacoDAO) {

        List<Reserva> lista = new ArrayList<>();
        JsonArray array = lerArray();

        // percorre cada objeto do JSON
        for (var elemento : array) {

            JsonObject obj = elemento.getAsJsonObject();

            String idReserva = obj.get("idReserva").getAsString();
            String idEspaco = obj.get("idEspaco").getAsString();

            LocalDateTime inicio = LocalDateTime.parse(obj.get("inicio").getAsString());
            LocalDateTime fim = LocalDateTime.parse(obj.get("fim").getAsString());
            double total = obj.get("valorTotal").getAsDouble();

            // RECONSTROI O ESPAÇO CORRETAMENTE
            Espaco espaco = espacoDAO.buscarPorId(idEspaco);

            // construtor da sua classe Reserva
            Reserva r = new Reserva(idReserva, espaco, inicio, fim);

            lista.add(r);
        }

        return lista;
    }

    // ======================================================
    // BUSCAR RESERVA POR ID
    // ======================================================
    public Reserva buscarPorId(String idReserva, EspacoDAO espacoDAO) {

        JsonArray array = lerArray();

        for (var elemento : array) {

            JsonObject obj = elemento.getAsJsonObject();

            if (obj.get("idReserva").getAsString().equals(idReserva)) {

                String idEspaco = obj.get("idEspaco").getAsString();

                LocalDateTime inicio = LocalDateTime.parse(obj.get("inicio").getAsString());
                LocalDateTime fim = LocalDateTime.parse(obj.get("fim").getAsString());

                Espaco espaco = espacoDAO.buscarPorId(idEspaco);

                return new Reserva(idReserva, espaco, inicio, fim);
            }
        }

        return null;
    }

    // ======================================================
    // BUSCAR TODAS AS RESERVAS DE UM ESPAÇO
    // ======================================================
    public List<Reserva> buscarReservasPorEspaco(String idEspaco, EspacoDAO espacoDAO) {

        List<Reserva> lista = new ArrayList<>();
        JsonArray array = lerArray();

        for (var elemento : array) {

            JsonObject obj = elemento.getAsJsonObject();

            if (obj.get("idEspaco").getAsString().equals(idEspaco)) {

                LocalDateTime inicio = LocalDateTime.parse(obj.get("inicio").getAsString());
                LocalDateTime fim = LocalDateTime.parse(obj.get("fim").getAsString());
                String idReserva = obj.get("idReserva").getAsString();

                Espaco espaco = espacoDAO.buscarPorId(idEspaco);

                lista.add(new Reserva(idReserva, espaco, inicio, fim));
            }
        }

        return lista;
    }

}