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

import service.EspacoService;
import service.ReservaService;

import Utils.Validacao;
import dao.EspacoDAO;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static EspacoService espacoService = new EspacoService();
    private static ReservaService reservaService = new ReservaService();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1 - Cadastrar Espaço");
            System.out.println("2 - Listar Espaços");
            System.out.println("3 - Buscar Espaço por ID");
            System.out.println("4 - Criar Reserva");
            System.out.println("5 - Listar Reservas");
            System.out.println("6 - Buscar Reserva por ID");
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

    // ===============================================================
    // MÉTODO DE LEITURA SEGURA DE DATA/HORA
    // ===============================================================
    private static LocalDateTime lerDataHora(String msg) {
        System.out.println(msg + " (formato: yyyy-MM-dd HH:mm)");

        while (true) {
            try {
                String txt = sc.nextLine().trim();

                // permite tanto espaço quanto o T
                txt = txt.replace(" ", "T");

                return LocalDateTime.parse(txt);

            } catch (Exception e) {
                System.out.println(" Formato inválido! Use: yyyy-MM-dd HH:mm");
            }
        }
    }

    // ===============================================================
    // CADASTRAR ESPAÇO
    // ===============================================================
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

    // ===============================================================
    // LISTAR ESPAÇOS
    // ===============================================================
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

    // ===============================================================
    // BUSCAR ESPAÇO POR ID
    // ===============================================================
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

    // ===============================================================
    // CRIAR RESERVA
    // Agora usa o método lerDataHora()
    // ===============================================================
    private static void criarReserva() throws CampoVazioException {

        // DEBUG PARA DESCOBRIR POR QUE O ESPAÇO ESTÁ VINDO NULL
        //EspacoDAO espacoDAO = new EspacoDAO();
        //System.out.println("\n---- DEBUG: Espaços carregados ----");
        //List<Espaco> debugLista = espacoDAO.listarTodos();

        //if (debugLista.isEmpty()) {
            //System.out.println("Nenhum espaço foi carregado! O arquivo Espacos.json pode estar vazio ou no caminho errado.");
        //} else {
            //for (Espaco e : debugLista) {
                //System.out.println("ID: " + e.getId() + " | Nome: " + e.getNome());
            //}
        //}
        //System.out.println("-----------------------------------\n");


        System.out.println("\n=== CRIAR RESERVA ===");

        System.out.print("ID da reserva: ");
        String idReserva = sc.nextLine();
        Validacao.obrigatorio(idReserva, "ID Reserva");

        System.out.print("ID do espaço: ");
        String idEspaco = sc.nextLine();
        Validacao.obrigatorio(idEspaco, "ID Espaço");

        System.out.print("Início (formato: yyyy-MM-dd HH:mm): ");
        String inicioStr = sc.nextLine();
        Validacao.obrigatorio(inicioStr, "Início");
        LocalDateTime inicio = LocalDateTime.parse(inicioStr.replace(" ", "T"));

        System.out.print("Fim (formato: yyyy-MM-dd HH:mm): ");
        String fimStr = sc.nextLine();
        Validacao.obrigatorio(fimStr, "Fim");
        LocalDateTime fim = LocalDateTime.parse(fimStr.replace(" ", "T"));

        reservaService.criarReserva(idReserva, idEspaco, inicio, fim);
        System.out.println("✔ Reserva criada com sucesso!");
    }

    // ===============================================================
    // LISTAR RESERVAS
    // ===============================================================
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

    // ===============================================================
    // BUSCAR RESERVA POR ID
    // ===============================================================
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
}