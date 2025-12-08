package service;

import java.time.LocalDateTime;
import java.util.List;

import dao.ReservaDAO;
import dao.EspacoDAO;

import model.ReservaStatus;
import model.Espaco;
import model.Reserva;

public class ReservaService {

    private ReservaDAO reservaDAO = new ReservaDAO();
    private EspacoDAO espacoDAO = new EspacoDAO();
    
    public void criarReserva(String idReserva, String idEspaco, LocalDateTime inicio, LocalDateTime fim) {

        Espaco espaco = espacoDAO.buscarPorId(idEspaco);
        if (espaco == null) {
            throw new RuntimeException("Erro: O espaço informado não existe.");
        }

        if (fim.isBefore(inicio) || fim.isEqual(inicio)) {
            throw new RuntimeException("Erro: O horário final deve ser depois do horário inicial.");
        }

        if (inicio.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Erro: Não é permitido reservar horários que já passaram.");
        }

        if (existeConflito(idEspaco, inicio, fim)) {
            throw new RuntimeException("Erro: Já existe uma reserva nesse período.");
        }

        Reserva nova = new Reserva(idReserva, espaco, inicio, fim);

        reservaDAO.salvarReserva(nova);

        atualizarDisponibilidade(espaco);
    }

    public List<Reserva> listarTodas() {
        atualizarTodasDisponibilidades();
        return reservaDAO.listarTodos(espacoDAO);
    }

    public Reserva buscarPorId(String idReserva) {
        return reservaDAO.buscarPorId(idReserva, espacoDAO);
    }
    
    private boolean existeConflito(String idEspaco, LocalDateTime inicio, LocalDateTime fim) {

        List<Reserva> todas = reservaDAO.listarTodos(espacoDAO);

        for (Reserva r : todas) {

            if (!r.getEspaco().getId().equals(idEspaco)) {
                continue;
            }

            boolean overlap =
                    inicio.isBefore(r.getFim()) &&
                    fim.isAfter(r.getInicio());

            if (overlap) {
                return true;
            }
        }

        return false;
    }
    
    private void atualizarDisponibilidade(Espaco espaco) {

        LocalDateTime agora = LocalDateTime.now();
        List<Reserva> reservas = reservaDAO.listarTodos(espacoDAO);

        boolean ocupadoAgora = false;

        for (Reserva r : reservas) {

            if (!r.getEspaco().getId().equals(espaco.getId())) continue;

            boolean dentroDoPeriodo =
                    agora.isAfter(r.getInicio()) &&
                    agora.isBefore(r.getFim());

            if (dentroDoPeriodo) {
                ocupadoAgora = true;
                break;
            }
        }

        espaco.setDisponivel(!ocupadoAgora);

        espacoDAO.salvarAtualizacao(espaco);
    }
    
    public void atualizarTodasDisponibilidades() {

        List<Espaco> lista = espacoDAO.listarTodos();

        for (Espaco e : lista) {
            atualizarDisponibilidade(e);
        }
    }
    public double cancelarReserva(String idReserva) {

        Reserva r = reservaDAO.buscarPorId(idReserva, espacoDAO);

        if (r == null) {
            throw new RuntimeException("Reserva não encontrada.");
        }

        if (r.getStatus() == ReservaStatus.CANCELADA) {
            throw new RuntimeException("Esta reserva já está cancelada.");
        }

        LocalDateTime agora = LocalDateTime.now();
        long horas = java.time.Duration.between(agora, r.getInicio()).toHours();

        double taxa = 0.0;

        if (horas < 24) {
            taxa = r.getValorTotal() * 0.20;
        }

        r.setStatus(ReservaStatus.CANCELADA);

        reservaDAO.atualizar(r);

        return taxa;
    }
}
