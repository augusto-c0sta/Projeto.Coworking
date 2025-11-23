package model;

public class CabineIndividual extends Espaco {
	
	private boolean maisDe4Horas;

    public CabineIndividual() {
        super();
    }

    public CabineIndividual(String id, String nome, int capacidade, boolean disponivel,double precoPorHora, 
    						boolean maisDe4Horas) {
        super(id, nome, capacidade, disponivel, precoPorHora);
        this.maisDe4Horas=maisDe4Horas;
    }

    @Override
    public double calcularCustoReserva(double horas) {
        double total = horas * getPrecoPorHora();

        // desconto de 10% acima de 4 horas
        if (horas > 4) {
            total *= 0.90; // aplica 10% de desconto
        }

        return total;
    }

    @Override
    public String toString() {
        return super.toString() + " | Tipo: Cabine Individual";
    }
}
