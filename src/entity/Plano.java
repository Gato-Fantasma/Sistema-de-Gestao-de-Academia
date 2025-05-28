package entity;
// Importa a classe UUID para geração de identificadores únicos.
import java.util.UUID;

/**
 * Classe que representa um plano de academia (como mensal, trimestral, etc.).
 */
public class Plano {

    // Identificador único do plano (gerado automaticamente).
    private UUID id;
    private int idBanco;

    // Tipo do plano, como "mensal", "anual", etc.
    private String tipo;

    /**
     * Construtor que cria um plano com o tipo informado e gera um ID único automaticamente.
     *
     * @param tipo Tipo do plano (ex: "mensal", "semestral").
     */
    public Plano(String tipo) {
        this.id = UUID.randomUUID(); // Gera um ID único para o plano.
        this.tipo = tipo;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public int getIdBanco() {
        return idBanco;
    }

    /**
     * Retorna o tipo do plano.
     *
     * @return Tipo como uma string.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna a representação em texto do plano.
     * Aqui, retorna apenas o tipo, facilitando a exibição em listas ou menus.
     *
     * @return Tipo do plano como string.
     */
    @Override
    public String toString() {
        return tipo;
    }
}
