package conexao;

import java.sql.Connection;

public class TestaConexao {
    public static void main(String[] args) {
        Connection conn = conexao.getConexao();
        if (conn != null) {
            System.out.println("Conexão bem-sucedida!");
        } else {
            System.out.println("Falha na conexão.");
        }
    }
}