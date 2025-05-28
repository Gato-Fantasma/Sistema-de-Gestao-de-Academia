package entity;
// Define um tipo enumerado para representar o status de um aluno na academia.
public enum StatusAluno {
    // Valores possíveis para o status de um aluno.
    ATIVO(1, "ativo"),
    INATIVO(2, "inativo");

    private int idBanco;
    private String nome;

    StatusAluno(int idBanco, String nome) {
        this.idBanco = idBanco;
        this.nome = nome;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Converte uma string ("ativo" ou "inativo") para o enum correspondente.
     * Se a string for "ativo" (ignorando maiúsculas/minúsculas), retorna ATIVO.
     * Qualquer outro valor retorna INATIVO por padrão.
     */
    public static StatusAluno fromString(String valor) {
        for (StatusAluno status : StatusAluno.values()) {
            if (status.nome.equalsIgnoreCase(valor)) {
                return status;
            }
        }
        return INATIVO; // padrão
    }


    /**
     * Sobrescreve o método toString() para exibir o nome do status em letras minúsculas.
     * Ex: ATIVO -> "ativo", INATIVO -> "inativo".
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
