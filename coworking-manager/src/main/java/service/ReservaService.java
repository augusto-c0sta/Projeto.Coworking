package service;

import java.time.LocalDateTime;
import java.util.List;

import dao.ReservaDAO;
import dao.EspacoDAO;

import model.Espaco;
import model.Reserva;

public class ReservaService {

    private ReservaDAO reservaDAO = new ReservaDAO();
    private EspacoDAO espacoDAO = new EspacoDAO();

    // ============================================================
    // CRIAR RESERVA
    // ============================================================
    public void criarReserva(String idReserva, String idEspaco, LocalDateTime inicio, LocalDateTime fim) {

        // validar existencia do espaço
        Espaco espaco = espacoDAO.buscarPorId(idEspaco);
        if (espaco == null) {
            throw new RuntimeException("Erro: O espaço informado não existe.");
        }

        // validar datas
        if (fim.isBefore(inicio) || fim.isEqual(inicio)) {
            throw new RuntimeException("Erro: O horário final deve ser depois do horário inicial.");
        }

        if (inicio.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Erro: Não é permitido reservar horários que já passaram.");
        }

        // verificar conflito (sobreposição)
        if (existeConflito(idEspaco, inicio, fim)) {
            throw new RuntimeException("Erro: Já existe uma reserva nesse período.");
        }

        // criar reserva
        Reserva nova = new Reserva(idReserva, espaco, inicio, fim);

        reservaDAO.salvarReserva(nova);

        // após salvar a reserva atualizar disponibilidade automática
        atualizarDisponibilidade(espaco);
    }

    // ============================================================
    // LISTAR TODAS
    // ============================================================
    public List<Reserva> listarTodas() {
        // atualizar disponibilidade de todos os espaços antes de listar
        atualizarTodasDisponibilidades();
        return reservaDAO.listarTodos(espacoDAO);
    }

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    public Reserva buscarPorId(String idReserva) {
        return reservaDAO.buscarPorId(idReserva, espacoDAO);
    }

    // ============================================================
    // VERIFICAR CONFLITO DE HORÁRIO
    // ============================================================
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

    // ============================================================
    // ATUALIZA DISPONIBILIDADE DE UM ESPAÇO
    // ============================================================
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

        // Salva mudanças no arquivo
        espacoDAO.salvarAtualizacao(espaco);
    }

    // ============================================================
    // ATUALIZA DISPONIBILIDADE DE TODOS OS ESPAÇOS
    // ============================================================
    public void atualizarTodasDisponibilidades() {

        List<Espaco> lista = espacoDAO.listarTodos();

        for (Espaco e : lista) {
            atualizarDisponibilidade(e);
        }
    }
}
