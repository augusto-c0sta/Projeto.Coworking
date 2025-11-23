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

	@Override
	public double calcularCustoReserva(double horas) {
		double total=getPrecoPorHora()*horas;
		if(evento) {
			total += 100.00;
		}
		
		return total;
	}

	@Override
    public String toString() {
        return super.toString() + 
               " | Evento: " + (evento ? "Sim" : "Não");
    }

}
