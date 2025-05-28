package entity;
// Importa classes utilitárias: UUID para identificador único e List/ArrayList para lista de exercícios
import java.util.*;

/**
 * Classe que representa uma ficha de treino contendo uma lista de exercícios.
 */
public class FichaTreino {

    // Identificador único da ficha (gera automaticamente)
    private UUID id;
    private int idBanco;

    // define um atributo chamado plano
    private Plano plano;

    // Lista de exercícios da ficha
    private List<String> exercicios;

    /**
     * Construtor que inicializa a ficha com um ID único e uma lista vazia de exercícios.
     */
    public FichaTreino() {
        this.id = UUID.randomUUID();         // Gera um ID aleatório único
        this.exercicios = new ArrayList<>(); // Inicializa a lista de exercícios
    }

    // Getter e Setter para o plano
    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    //get
    public int getIdBanco() {
        return idBanco;
    }

    /**
     * Adiciona um exercício à ficha.
     *
     * @param exercicio Nome do exercício a ser adicionado.
     */
    public void adicionarExercicio(String exercicio) {
        exercicios.add(exercicio);
    }

    /**
     * Atualiza o nome de um exercício em um índice específico da lista.
     *
     * @param indice    Posição do exercício a ser atualizado.
     * @param exercicio Novo nome do exercício.
     */
    public void atualizarExercicio(int indice, String exercicio) {
        // Verifica se o índice é válido antes de atualizar
        if (indice >= 0 && indice < exercicios.size()) {
            exercicios.set(indice, exercicio);
        }
    }

    /**
     * Retorna a lista de exercícios da ficha.
     *
     * @return Lista de strings com os nomes dos exercícios.
     */
    public List<String> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<String> exercicios) {
        this.exercicios = exercicios;
    }

    /**
     * Retorna uma representação em texto da ficha de treino.
     * Caso a ficha esteja vazia, retorna uma mensagem apropriada.
     *
     * @return String contendo os exercícios separados por vírgula ou mensagem padrão.
     */
    @Override
    public String toString() {
        return exercicios.isEmpty() ? "Sem exercícios." : String.join(", ", exercicios);
    }
}
