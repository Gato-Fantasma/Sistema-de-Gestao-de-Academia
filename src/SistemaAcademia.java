import java.util.*;

import DAO.AlunoDao;
//import DAO.EnderecoDao;
import DAO.FichaTreinoDao;
import DAO.PlanoDao;
import DAO.StatusAlunoDao;
import entity.Aluno;
import entity.Endereco;
import entity.FichaTreino;
import entity.Plano;
import entity.StatusAluno;

/**
 * Classe principal que gerencia o sistema da academia com menus para administrador, instrutor e aluno.
 */
public class SistemaAcademia {
    private static final List<Aluno> alunos = new ArrayList<>();
    private static final List<Plano> planos = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicializa os status padrão na tabela sql status_aluno
        new StatusAlunoDao().inicializarStatusPadrao();

        int perfil;
        do {
            System.out.println("\n=== SELECIONE O PERFIL ===");
            System.out.println("1. Administrador");
            System.out.println("2. Instrutor");
            System.out.println("3. Aluno");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            try {
                perfil = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                perfil = -1;
            }

            // Direciona para o menu correspondente
            switch (perfil) {
                case 1 -> menuAdministrador();
                case 2 -> menuInstrutor();
                case 3 -> menuAluno();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (perfil != 0);
    }

    // ========== MENUS ==========
    private static void menuAdministrador() {
        int opcao;
        do {
            System.out.println("\n=== MENU ADMINISTRADOR ===");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Cadastrar Plano");
            System.out.println("3. Excluir Aluno");
            System.out.println("4. Excluir Plano");
            System.out.println("5. Listar Alunos com Filtros");
            System.out.println("6. Listar Planos");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarAluno();
                case 2 -> cadastrarPlano();
                case 3 -> excluirAluno();
                case 4 -> excluirPlano();
                case 5 -> listarAlunosComFiltro();
                case 6 -> listarPlanos();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuInstrutor() {
        int opcao;
        do {
            System.out.println("\n=== MENU INSTRUTOR ===");
            System.out.println("1. Criar/Atualizar Ficha de Treino");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> criarOuAtualizarFicha();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuAluno() {
        int opcao;
        do {
            System.out.println("\n=== MENU ALUNO ===");
            System.out.println("1. Consultar Plano e Ficha de Treino");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> consultarAluno();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // ========== FUNCIONALIDADES ==========

    /**
     * Cadastra um novo aluno com nome, status, plano e endereço.
     */
    private static void cadastrarAluno() {
        try {
            System.out.print("Nome do aluno: ");
            String nome = scanner.nextLine();

            System.out.print("Status (ativo/inativo): ");
            String statusInput = scanner.nextLine();
            StatusAluno status = StatusAluno.fromString(statusInput);

            if (planos.isEmpty()) {
                System.out.println("Nenhum plano cadastrado. Cadastre um plano primeiro.");
                return;
            }

            System.out.println("Planos disponíveis:");
            for (int i = 0; i < planos.size(); i++) {
                System.out.println(i + ". " + planos.get(i).getTipo());
            }

            System.out.print("Escolha o plano (índice): ");
            int planoIndex = Integer.parseInt(scanner.nextLine());
            Plano plano = planos.get(planoIndex);

            // Coleta de endereço
            System.out.println("== Endereço ==");
            System.out.print("Rua: ");
            String rua = scanner.nextLine();
            System.out.print("Número: ");
            String numero = scanner.nextLine();
            System.out.print("Bairro: ");
            String bairro = scanner.nextLine();
            System.out.print("Cidade: ");
            String cidade = scanner.nextLine();
            System.out.print("Estado: ");
            String estado = scanner.nextLine();
            System.out.print("CEP: ");
            String cep = scanner.nextLine();

            Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep);

            Aluno aluno = new Aluno(nome, status, plano, endereco);
            alunos.add(aluno);
            System.out.println("Aluno cadastrado com sucesso.");

            //enviar o endereço para o sql
            new AlunoDao().salvar(aluno);

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    /**
     * Cadastra um novo plano.
     */
    private static void cadastrarPlano() {
        System.out.print("Tipo de plano: ");
        String tipo = scanner.nextLine();

        Plano plano = new Plano(tipo); // Cria o plano uma única vez
        new PlanoDao().salvar(plano); // Salva e preenche o idBanco

        planos.add(plano); // Usa o mesmo objeto já preenchido
        System.out.println("Plano cadastrado com sucesso.");
    }

    /**
     * Remove um aluno pelo nome.
     */
    private static void excluirAluno() {
        System.out.print("Digite o nome do aluno a ser removido: ");
        String nome = scanner.nextLine();
        boolean removido = alunos.removeIf(a -> a.getNome().equalsIgnoreCase(nome));
        System.out.println(removido ? "Aluno removido." : "Aluno não encontrado.");
    }

    /**
     * Remove um plano pelo tipo.
     */
    private static void excluirPlano() {
        System.out.print("Digite o tipo do plano a ser removido: ");
        String tipo = scanner.nextLine();
        boolean removido = planos.removeIf(p -> p.getTipo().equalsIgnoreCase(tipo));
        System.out.println(removido ? "Plano removido." : "Plano não encontrado.");
    }

    /**
     * Lista os alunos com filtros opcionais de status e tipo de plano.
     */
    private static void listarAlunosComFiltro() {
        System.out.print("Filtrar por status (ativo/inativo ou vazio para todos): ");
        String statusFiltro = scanner.nextLine();

        System.out.print("Filtrar por tipo de plano (ou vazio para todos): ");
        String tipoFiltro = scanner.nextLine();

        for (Aluno aluno : alunos) {
            boolean statusMatch = statusFiltro.isEmpty() || aluno.getStatus().toString().equalsIgnoreCase(statusFiltro);
            boolean tipoMatch = tipoFiltro.isEmpty() || aluno.getPlano().getTipo().equalsIgnoreCase(tipoFiltro);
            if (statusMatch && tipoMatch) {
                System.out.println(aluno);
            }
        }
    }

    /**
     * Lista todos os planos cadastrados.
     */
    private static void listarPlanos() {
        if (planos.isEmpty()) {
            System.out.println("Nenhum plano cadastrado.");
        } else {
            for (Plano plano : planos) {
                System.out.println(plano);
            }
        }
    }

    /**
     * Permite que o instrutor crie ou atualize a ficha de treino de um aluno.
     */
    private static void criarOuAtualizarFicha() {
        try {
            System.out.print("Nome do aluno: ");
            String nome = scanner.nextLine();

            Aluno aluno = buscarAlunoPorNome(nome);
            if (aluno == null) {
                System.out.println("Aluno não encontrado.");
                return;
            }

            // Lista para armazenar exercícios temporariamente
            List<String> listaExercicios = new ArrayList<>();
            System.out.println("Digite os exercícios (pressione Enter sem digitar para finalizar):");

            while (true) {
                System.out.print("Adicionar exercício: ");
                String exercicio = scanner.nextLine().trim();
                if (exercicio.isEmpty()) {
                    break; // Para o loop ao detectar linha vazia
                }
                listaExercicios.add(exercicio);
            }

            if (listaExercicios.isEmpty()) {
                System.out.println("Nenhum exercício adicionado.");
                return;
            }

            // Se não tem ficha, cria nova
            if (aluno.getFichaTreino() == null) {
                FichaTreino ficha = new FichaTreino();
                ficha.setPlano(aluno.getPlano());
                ficha.setExercicios(listaExercicios);
                aluno.setFichaTreino(ficha);

                new FichaTreinoDao().salvar(ficha, aluno);
            } else {
                // Se já existe ficha, atualiza a lista de exercícios dela e salva novamente
                FichaTreino ficha = aluno.getFichaTreino();
                ficha.getExercicios().addAll(listaExercicios);

                new FichaTreinoDao().salvar(ficha, aluno);
            }

            System.out.println("Ficha de treino atualizada com " + listaExercicios.size() + " exercícios.");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar ficha: " + e.getMessage());
        }
    }


    /**
     * Permite ao aluno consultar seu plano e ficha de treino.
     */
    private static void consultarAluno() {
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        Aluno aluno = buscarAlunoPorNome(nome);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("Plano: " + aluno.getPlano());
        System.out.println("Endereço: " + aluno.getEndereco());
        System.out.println("Ficha de Treino: " + aluno.getFichaTreino());
    }

    /**
     * Busca um aluno pelo nome (ignorando maiúsculas/minúsculas).
     */
    private static Aluno buscarAlunoPorNome(String nome) {
        return alunos.stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);
    }
}
