package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.Espaco;
import model.Reserva;
import model.ReservaStatus;

public class ReservaDAO extends DAO {

    public ReservaDAO() {
        super("Reservas");
    }

    public void salvarReserva(Reserva reserva) {

        JsonArray array = lerArray();
        JsonObject obj = new JsonObject();

        obj.addProperty("idReserva", reserva.getIdReserva());
        obj.addProperty("idEspaco", reserva.getEspaco().getId());
        obj.addProperty("inicio", reserva.getInicio().toString());
        obj.addProperty("fim", reserva.getFim().toString());
        obj.addProperty("valorTotal", reserva.getValorTotal());
        obj.addProperty("status", reserva.getStatus().name());

        array.add(obj);
        salvarArray(array);
    }

    public List<Reserva> listarTodos(EspacoDAO espacoDAO) {

        List<Reserva> lista = new ArrayList<>();
        JsonArray array = lerArray();

        for (var elemento : array) {

            JsonObject obj = elemento.getAsJsonObject();

            String idReserva = obj.get("idReserva").getAsString();
            String idEspaco = obj.get("idEspaco").getAsString();

            LocalDateTime inicio = LocalDateTime.parse(obj.get("inicio").getAsString());
            LocalDateTime fim = LocalDateTime.parse(obj.get("fim").getAsString());
            double total = obj.get("valorTotal").getAsDouble();

            Espaco espaco = espacoDAO.buscarPorId(idEspaco);

            String statusStr = obj.has("status") && !obj.get("status").isJsonNull()
                    ? obj.get("status").getAsString()
                    : "ATIVA";
            ReservaStatus status = ReservaStatus.valueOf(statusStr);
            
            Reserva r = new Reserva(idReserva, espaco, inicio, fim,  total, status);
            r.setValorTotal(total);

            lista.add(r);
        }

        return lista;
    }

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
    public void atualizar(Reserva reserva) {
        JsonArray array = lerArray();
        JsonArray novo = new JsonArray();

        for (var elemento : array) {
            JsonObject obj = elemento.getAsJsonObject();
            if (obj.get("idReserva").getAsString().equals(reserva.getIdReserva())) {

                JsonObject u = new JsonObject();
                u.addProperty("idReserva", reserva.getIdReserva());
                u.addProperty("idEspaco", reserva.getEspaco().getId());
                u.addProperty("inicio", reserva.getInicio().toString());
                u.addProperty("fim", reserva.getFim().toString());
                u.addProperty("valorTotal", reserva.getValorTotal());
                u.addProperty("status", reserva.getStatus().name());
                novo.add(u);
            } else {
                novo.add(obj);
            }
        }

        salvarArray(novo);
    }

}