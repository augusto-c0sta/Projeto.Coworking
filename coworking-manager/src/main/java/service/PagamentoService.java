package service;

import java.time.LocalDateTime;

import java.util.List;
import dao.PagamentoDAO;
import dao.ReservaDAO;
import model.Pagamento;
import model.Reserva;
import model.ReservaStatus;

public class PagamentoService {

    private PagamentoDAO pagamentoDAO = new PagamentoDAO();
    private ReservaDAO reservaDAO = new ReservaDAO();

    public void processarPagamento(String idPagamento, String idReserva, double valor, String metodo) {

        Reserva r = reservaDAO.buscarPorId(idReserva, new dao.EspacoDAO());
        if (r == null) throw new RuntimeException("Reserva não encontrada");

        Pagamento p = new Pagamento(idPagamento, idReserva, valor, LocalDateTime.now(), metodo);
        pagamentoDAO.salvarPagamento(p);

        r.setStatus(ReservaStatus.CONCLUIDA);
        reservaDAO.atualizar(r);
    }
    public List<Pagamento> listarPagamentos() {
        return pagamentoDAO.listarTodos();
    }
}