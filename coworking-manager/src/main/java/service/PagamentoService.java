package service;

import java.time.LocalDateTime;
import java.util.List;

import dao.PagamentoDAO;
import model.Pagamento;
import model.Reserva;

public class PagamentoService {

    private PagamentoDAO pagamentoDAO = new PagamentoDAO();
    private ReservaService reservaService;

    public PagamentoService(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Registrar pagamento para uma reserva existente
    public void registrarPagamento(String idPagamento, String idReserva, String metodo) {

        Reserva r = reservaService.buscarPorId(idReserva);

        if (r == null) {
            throw new RuntimeException("Reserva não encontrada.");
        }

        if (!r.getStatus().equals("ativa")) {
            throw new RuntimeException("Apenas reservas ativas podem ser pagas.");
        }

        double valor = r.getValorTotal();

        Pagamento p = new Pagamento(
                idPagamento,
                idReserva,
                valor,
                LocalDateTime.now(),
                metodo
        );

        pagamentoDAO.salvarPagamento(p);
    }

    public Pagamento buscarPorId(String idPagamento) {
        return pagamentoDAO.buscarPorId(idPagamento);
    }

    public List<Pagamento> listarTodos() {
        return pagamentoDAO.listarTodos();
    }
}