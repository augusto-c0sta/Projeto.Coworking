package model;

public class SalaDeReuniao extends Espaco {

    private boolean usoDoProjetor;

    public SalaDeReuniao() {
        super();
    }

    public SalaDeReuniao(String id, String nome, int capacidade, boolean disponivel, double precoPorHora, 
    					boolean usoDoProjetor) {
        super(id, nome, capacidade, disponivel, precoPorHora);
        this.usoDoProjetor = usoDoProjetor;
    }
    
    //método abstrato sobrescrito
    @Override    
    public double calcularCustoReserva(double horas) {
        double total = horas * getPrecoPorHora();
        
        // taxa fixa para uso de projetor
        if (usoDoProjetor) {        	
            total += 15.00; 
        }
        return total;
    }
    
    //Getters e Setters

    public boolean isUsoDoProjetor() {
        return usoDoProjetor;
    }

    public void setUsoDoProjetor(boolean usoDoProjetor) {
        this.usoDoProjetor = usoDoProjetor;
    }
    
    //toString
    @Override  
    public String toString() {
        return super.toString() +
               "\nUso do projetor: " + (usoDoProjetor ? "Sim" : "Não") +
               "\n";
    }
}
