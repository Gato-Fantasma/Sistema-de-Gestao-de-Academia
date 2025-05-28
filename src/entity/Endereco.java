package entity;
// Classe que representa o endereço de um aluno.
public class Endereco {
    // Atributos que compõem o endereço.
    private String rua, numero, bairro, cidade, estado, cep;

    public Endereco(String rua, String numero, String bairro, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    /**
     * Retorna uma representação legível do endereço em formato de texto.
     *
     * Exemplo de saída:
     * Rua das Flores, 123 - Centro, São Paulo - SP, CEP: 01000-000
     */
    @Override
    public String toString() {
        return rua + ", " + numero + " - " + bairro + ", " + cidade + " - " + estado + ", CEP: " + cep;
    }
}
