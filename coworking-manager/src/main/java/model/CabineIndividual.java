package model;

public class CabineIndividual extends Espaco {
	
    public CabineIndividual() {
        super();
    }

    public CabineIndividual(String id, String nome, int capacidade, boolean disponivel,double precoPorHora) {
        super(id, nome, capacidade, disponivel, precoPorHora);
    }
    
    @Override    
    public double calcularCustoReserva(double horas) {
        double total = horas * getPrecoPorHora();
           
        if (horas>4) {
            total *= 0.90; 
        }

        return total;
	}

    @Override 
    public String toString() {
        return super.toString() + 
        		"\nDesconto de 10% para reservas acima de 4 Horas ";
    }
}
