package model;

import java.time.LocalDateTime;

public class Pagamento {

    private String idPagamento;
    private String idReserva;
    private double valorPago;
    private LocalDateTime data;
    private String metodo; // "PIX", "CARTAO", "DINHEIRO"

    public Pagamento(String idPagamento, String idReserva, double valorPago, LocalDateTime data, String metodo) {
        this.idPagamento = idPagamento;
        this.idReserva = idReserva;
        this.valorPago = valorPago;
        this.data = data;
        this.metodo = metodo;
    }

    // getters/setters
    public String getIdPagamento() { return idPagamento; }
    public String getIdReserva() { return idReserva; }
    public double getValorPago() { return valorPago; }
    public LocalDateTime getData() { return data; }
    public String getMetodo() { return metodo; }
}