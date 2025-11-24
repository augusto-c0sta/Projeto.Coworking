package model;

public class Auditorio extends Espaco {
	
	private boolean evento;

	public Auditorio() {
		super();
	}
 
	public Auditorio(String id, String nome, int capacidade, boolean disponivel, double precoPorHora, 
					boolean evento) {
		super(id, nome, capacidade, disponivel, precoPorHora);
		this.evento=evento;
	}
	
	//método abstrato sobrescrito
	@Override	
	public double calcularCustoReserva(double horas) {
		double total=getPrecoPorHora()*horas;
		
		//taxa fixa para eventos
		if(evento) {			
			total += 100.00;
		}
		
		return total;
	}
	
	//Getters e Setters

	public boolean isEvento() {
		return evento;
	}

	public void setEvento(boolean evento) {
		this.evento = evento;
	}
	
	//toString
	@Override
	
    public String toString() {
        return super.toString() + 
               "\nEvento: " + (evento ? "Sim" : "Não") +
               "\n";
    }

}
