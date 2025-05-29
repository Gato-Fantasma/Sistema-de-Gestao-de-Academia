package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {

    private static final String url = "jdbc:mysql://localhost:3306/banco";
    private static final String user = "root";
    private static final String password = "";

    // NÃO guardar a conexão em uma variável estática
    public static Connection getConexao() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;    
        }
    }
}
