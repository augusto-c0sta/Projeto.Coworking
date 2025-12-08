package model;


import java.time.LocalDateTime;

public class Pagamento {

    private String idPagamento;
    private String idReserva;
    private double valorPago;
    private LocalDateTime dataPagamento;
    private String metodo; // Pix, Cartão, Dinheiro

    public Pagamento(String idPagamento, String idReserva, double valorPago,
                     LocalDateTime dataPagamento, String metodo) {
        this.idPagamento = idPagamento;
        this.idReserva = idReserva;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.metodo = metodo;
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public double getValorPago() {
        return valorPago;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public String getMetodo() {
        return metodo;
    }

    @Override
    public String toString() {
        return "\n=== PAGAMENTO ===" +
                "\nID do Pagamento: " + idPagamento +
                "\nID da Reserva: " + idReserva +
                "\nValor Pago: R$ " + valorPago +
                "\nData do Pagamento: " + dataPagamento +
                "\nMétodo: " + metodo;
    }
}