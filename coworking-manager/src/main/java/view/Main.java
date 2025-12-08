package view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import Exceptions.CampoVazioException;
import model.Auditorio;
import model.CabineIndividual;
import model.Espaco;
import model.Reserva;
import model.SalaDeReuniao;
import model.Pagamento;

import service.EspacoService;
import service.ReservaService;
import service.PagamentoService;

import Utils.Validacao;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static EspacoService espacoService = new EspacoService();
    private static ReservaService reservaService = new ReservaService();
    private static PagamentoService pagamentoService = new PagamentoService();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1 - Cadastrar Espaço");
            System.out.println("2 - Listar Espaços");
            System.out.println("3 - Buscar Espaço por ID");
            System.out.println("----------------------------------");
            System.out.println("4 - Criar Reserva");
            System.out.println("5 - Listar Reservas");
            System.out.println("6 - Buscar Reserva por ID");
            System.out.println("7 - Cancelar Reserva");
            System.out.println("----------------------------------");
            System.out.println("8 - Listar Pagamentos");
            System.out.println("----------------------------------");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            String opcao = sc.nextLine();

            try {

                switch (opcao) {

                    case "1" -> cadastrarEspaco();
                    case "2" -> listarEspacos();
                    case "3" -> buscarEspaco();
                    case "4" -> criarReserva();
                    case "5" -> listarReservas();
                    case "6" -> buscarReserva();
                    case "7" -> cancelarReserva();
                    case "8" -> listarPagamentos();

                    case "0" -> {
                        System.out.println("Encerrando o programa...");
                        return;
                    }

                    default -> System.out.println("❌ Opção inválida!");
                }

            } catch (CampoVazioException e) {
                System.out.println(" Erro: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(" Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(" Erro inesperado: " + e.getMessage());
            }

        }

    }

    // Cadastrar espaços
    
    private static void cadastrarEspaco() throws Exception {

        System.out.println("\n=== CADASTRO DE ESPAÇO ===");

        System.out.print("ID: ");
        String id = sc.nextLine();
        Validacao.obrigatorio(id, "ID");

        System.out.print("Nome: ");
        String nome = sc.nextLine();
        Validacao.obrigatorio(nome, "Nome");

        System.out.print("Capacidade: ");
        String capStr = sc.nextLine();
        Validacao.obrigatorio(capStr, "Capacidade");
        int capacidade = Integer.parseInt(capStr);

        System.out.print("Preço por hora: ");
        String precoStr = sc.nextLine();
        Validacao.obrigatorio(precoStr, "Preço por hora");
        double preco = Double.parseDouble(precoStr);

        System.out.println("""
                Tipo de espaço:
                1 - Sala de Reunião
                2 - Cabine Individual
                3 - Auditório
                """);

        System.out.print("Escolha o tipo: ");
        String tipo = sc.nextLine();
        Validacao.obrigatorio(tipo, "Tipo");

        Espaco novo;

        switch (tipo) {

            case "1" -> {
                System.out.print("Possui projetor? (true/false): ");
                boolean proj = Boolean.parseBoolean(sc.nextLine());
                novo = new SalaDeReuniao(id, nome, capacidade, true, preco, proj);
            }

            case "2" -> {
                novo = new CabineIndividual(id, nome, capacidade, true, preco);
            }

            case "3" -> {
                System.out.print("É para eventos? (true/false): ");
                boolean evento = Boolean.parseBoolean(sc.nextLine());
                novo = new Auditorio(id, nome, capacidade, true, preco, evento);
            }

            default -> {
                System.out.println(" Tipo inválido!");
                return;
            }
        }

        espacoService.cadastrarEspaco(novo);
        System.out.println(" Espaço cadastrado com sucesso!");
    }

    // Listar espaços
    
    private static void listarEspacos() {

        System.out.println("\n=== LISTA DE ESPAÇOS ===");

        List<Espaco> lista = espacoService.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum espaço cadastrado.");
            return;
        }

        for (Espaco e : lista) {
            System.out.println("-----------------------------");
            System.out.println(e);
        }
    }

    // Buscar espaço pelo ID
    
    private static void buscarEspaco() throws CampoVazioException {

        System.out.print("\nDigite o ID: ");
        String id = sc.nextLine();
        Validacao.obrigatorio(id, "ID");

        Espaco e = espacoService.buscarPorId(id);

        if (e == null) {
            System.out.println(" Espaço não encontrado.");
            return;
        }

        System.out.println("\n=== ESPAÇO ENCONTRADO ===");
        System.out.println(e);
    }

    // Criar reserva
    
    private static void criarReserva() throws CampoVazioException {

        System.out.println("\n=== CRIAR RESERVA ===");

        System.out.print("ID da reserva: ");
        String idReserva = sc.nextLine();
        Validacao.obrigatorio(idReserva, "ID Reserva");

        System.out.print("ID do espaço: ");
        String idEspaco = sc.nextLine();
        Validacao.obrigatorio(idEspaco, "ID Espaço");

        System.out.print("Início (AAAA-MM-DD HH:MM): ");
        String inicioStr = sc.nextLine();
        LocalDateTime inicio = LocalDateTime.parse(inicioStr.replace(" ", "T"));

        System.out.print("Fim (AAAA-MM-DD HH:MM): ");
        String fimStr = sc.nextLine();
        LocalDateTime fim = LocalDateTime.parse(fimStr.replace(" ", "T"));

        reservaService.criarReserva(idReserva, idEspaco, inicio, fim);
        System.out.println(" Reserva criada com sucesso!");
    }

    // Listar reservas
    
    private static void listarReservas() {

        System.out.println("\n=== LISTA DE RESERVAS ===");

        List<Reserva> lista = reservaService.listarTodas();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }

        for (Reserva r : lista) {
            System.out.println("-----------------------------");
            System.out.println(r);
        }
    }

    // Buscar reserva pelo id
    
    private static void buscarReserva() throws CampoVazioException {

        System.out.print("\nDigite o ID da reserva: ");
        String id = sc.nextLine();
        Validacao.obrigatorio(id, "ID Reserva");

        Reserva r = reservaService.buscarPorId(id);

        if (r == null) {
            System.out.println(" Reserva não encontrada.");
            return;
        }

        System.out.println("\n=== RESERVA ENCONTRADA ===");
        System.out.println(r);
    }

    // Cancelar reserva
    
    private static void cancelarReserva() throws CampoVazioException {

        System.out.print("\nID da reserva: ");
        String id = sc.nextLine();
        Validacao.obrigatorio(id, "ID Reserva");

        double taxa = reservaService.cancelarReserva(id);

        if (taxa > 0) {
            System.out.println("Reserva cancelada com taxa de 20%: R$ " + taxa);
        } else {
            System.out.println("Reserva cancelada sem custo!");
        }
    }

    // Listar pagamentos
    
    private static void listarPagamentos() {

        System.out.println("\n=== PAGAMENTOS ===");

        List<Pagamento> lista = pagamentoService.listarPagamentos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }

        for (Pagamento p : lista) {
            System.out.println("-----------------------------");
            System.out.println(p);
        }
    }
}