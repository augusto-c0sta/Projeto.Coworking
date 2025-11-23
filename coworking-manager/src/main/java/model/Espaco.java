package model;

public abstract class Espaco {
	
	private String id;
	private String nome;
	private int capacidade;
	protected boolean disponivel;
	protected double precoPorHora;
	public abstract double calcularCustoReserva(double horas);
	
	public Espaco() {
	}
	
	public Espaco(String id, String nome, int capacidade, boolean disponivel, double precoPorHora) {
		super();
		this.id=id;
		this.nome=nome;
		this.capacidade=capacidade;
		this.disponivel=disponivel;
		this.precoPorHora=precoPorHora;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public double getPrecoPorHora() {
		return precoPorHora;
	}

	public void setPrecoPorHora(double precoPorHora) {
		this.precoPorHora = precoPorHora;
	}
	
	public String toString() {
		String disp = disponivel ? "Sim" : "Não";
		
		return
	            "Id: " + id +
	            " | Nome: " + nome +
	            " | Capacidade: " + capacidade + " pessoas" +
	            " | Disponível: " + disp +
	            " | Preço por hora: R$ " + precoPorHora;
	}
	
}
