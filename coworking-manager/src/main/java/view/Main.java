package view;

import java.time.LocalDateTime;

import dao.EspacoDAO;
import dao.ReservaDAO;

import model.SalaDeReuniao;
import model.Espaco;
import model.Reserva;

public class Main {

    public static void main(String[] args) {

        // ======================
        // 1. Criar um espaço
        // ======================
        //SalaDeReuniao sala = new SalaDeReuniao(
                //"S178",
                //"Sala Azul dos marinheiros",
                //10,
                //true,
                //50.0,
                //true
        //);

        // ======================
        // 2. Salvar espaço
        // ======================
        EspacoDAO espacoDAO = new EspacoDAO();
        //espacoDAO.salvar(sala);
        //System.out.println("Espaço salvo!");

        // ======================
        // 3. Buscar espaço pelo ID
        // ======================
        Espaco espaco = espacoDAO.buscarPorId("S178");
        //System.out.println("Espaço encontrado: " + espaco.getNome());

        // ======================
        // 4. Criar reserva
        // ======================
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 10, 14, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 1, 10, 17, 0);

        Reserva reserva = new Reserva("R1", espaco, inicio, fim);

        // ======================
        // 5. Salvar reserva
        // ======================
        ReservaDAO reservaDAO = new ReservaDAO();
        reservaDAO.salvarReserva(reserva);
        System.out.println("Reserva salva!");
        
    }
}