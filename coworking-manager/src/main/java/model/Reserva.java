package model;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Reserva {

    private String idReserva;
    private Espaco espaco;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private double valorTotal;
    private ReservaStatus status; // <--- novo

    // Construtor atual (mantém cálculo automático)
    public Reserva(String idReserva, Espaco espaco, LocalDateTime inicio, LocalDateTime fim) {
        this.idReserva = idReserva;
        this.espaco = espaco;
        this.inicio = inicio;
        this.fim = fim;
        double horas = calcularHoras();
        this.valorTotal = espaco.calcularCustoReserva(horas);
        this.status = ReservaStatus.ATIVA; // padrão ao criar
    }

    // Construtor adicional para carregar do JSON com valor e status já definidos
    public Reserva(String idReserva, Espaco espaco, LocalDateTime inicio, LocalDateTime fim, double valorTotal, ReservaStatus status) {
        this.idReserva = idReserva;
        this.espaco = espaco;
        this.inicio = inicio;
        this.fim = fim;
        this.valorTotal = valorTotal;
        this.status = status != null ? status : ReservaStatus.ATIVA;
    }

    //getters/setters

	 public String getIdReserva() {
		 return idReserva;
	 }
	 public void setIdReserva(String idReserva) {
		 this.idReserva = idReserva;
	 }
	 public Espaco getEspaco() {
		 return espaco;
	 }
	 public void setEspaco(Espaco espaco) {
		 this.espaco = espaco;
	 }
	 public LocalDateTime getInicio() {
		 return inicio;
	 }
	 public void setInicio(LocalDateTime inicio) {
		 this.inicio = inicio;
	 }
	 public LocalDateTime getFim() {
		 return fim;
	 }
	 public void setFim(LocalDateTime fim) {
		 this.fim = fim;
	 }
	 public double getValorTotal() {
		 return valorTotal;
	 }
	 public void setValorTotal(double valorTotal) {
		 this.valorTotal = valorTotal;
	 }
	 
    public ReservaStatus getStatus() { 
    	return status; }
    
    public void setStatus(ReservaStatus status) { 
    	this.status = status; }
    
    private double calcularHoras() {
        long minutos = Duration.between(inicio, fim).toMinutes();
        return minutos / 60.0;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Id da Reserva: " + idReserva +
               " | Local: " + (espaco != null ? espaco.getNome() : "N/A") +
               " | Inicio: " + inicio.format(fmt) +
               " | Fim: " + fim.format(fmt) +
               " | Valor total: R$" + String.format("%.2f", valorTotal) +
               " | Status: " + (status != null ? status : ReservaStatus.ATIVA);
    }
}
