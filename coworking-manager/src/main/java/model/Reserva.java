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
	
	public Reserva(String idReserva, Espaco espaco, LocalDateTime inicio, LocalDateTime fim) {
		this.idReserva=idReserva;
		this.espaco=espaco;
		this.inicio=inicio;
		this.fim=fim;
		
		double horas=calcularHoras();
		this.valorTotal=espaco.calcularCustoReserva(horas);
	}
	
	 //calcula horas com base na duração do agendamento	
	 private double calcularHoras() {		 
		 
		 //converte as horas em minutos e retorna o tempo entre o inicio e o fim já convertido em horas	 
		 long minutos=Duration.between(inicio,fim).toMinutes();
		 return minutos/60.0;
	 }
	 
	 //Getters e Setters

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
	 
	 //toString
	 @Override
	 public String toString() {
		 
		 //formatador de tempo em string 
		 DateTimeFormatter fmt=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		 
		 return 
				 "\nId da Reserva: " + idReserva +
				 "\nLocal: " + espaco.getNome() +
				 "\nInicio da reserva: " + inicio.format(fmt) +
				 "\nFim da reserva: " + fim.format(fmt) +
				 "\nValor total: R$" + String.format("%.2f", valorTotal) +
				 "\n";
		 
	 }

}
