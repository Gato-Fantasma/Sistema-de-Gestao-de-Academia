package entity;
// Importa a classe UUID, que é usada para gerar identificadores únicos.
import java.util.UUID;

// Classe que representa um aluno da academia.
public class Aluno {
    // Identificador único do aluno, gerado automaticamente.
    private UUID id;

    private int idBanco; // ID gerado pelo banco de dados

    // Nome do aluno.
    private String nome;

    // Status do aluno (ATIVO ou INATIVO), definido por um enum.
    private StatusAluno status;

    // Plano de treino associado ao aluno.
    private Plano plano;

    // Ficha de treino do aluno, que pode conter os exercícios.
    private FichaTreino fichaTreino;

    // Endereço do aluno.
    private Endereco endereco;

    /**
     * Construtor da classe Aluno.
     * Gera um ID único automaticamente e define os dados fornecidos.
     */
    public Aluno(String nome, StatusAluno status, Plano plano, Endereco endereco) {
        this.id = UUID.randomUUID(); // Gera um UUID único para cada aluno.
        this.nome = nome;
        this.status = status;
        this.plano = plano;
        this.endereco = endereco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public int getIdBanco() {
        return idBanco;
    }

    // Retorna o nome do aluno.
    public String getNome() {
        return nome;
    }

    // Retorna o status do aluno (ATIVO/INATIVO).
    public StatusAluno getStatus() {
        return status;
    }

    // Retorna o plano do aluno.
    public Plano getPlano() {
        return plano;
    }

    // Retorna o endereço do aluno.
    public Endereco getEndereco() {
        return endereco;
    }

    // Retorna a ficha de treino do aluno.
    public FichaTreino getFichaTreino() {
        return fichaTreino;
    }

    // Define (ou atualiza) a ficha de treino do aluno.
    public void setFichaTreino(FichaTreino fichaTreino) {
        this.fichaTreino = fichaTreino;
    }

    /**
     * Representação em string do objeto Aluno.
     * Exibe nome, status, tipo de plano, endereço e a ficha (se houver).
     */
    @Override
    public String toString() {
        return nome + " (" + status + ") - Plano: " + plano.getTipo() +
                "\nEndereço: " + endereco +
                "\nFicha: " + (fichaTreino != null ? fichaTreino : "Sem ficha");
    }
}
