package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import conexao.conexao;

public class StatusAlunoDao {

    public void inicializarStatusPadrao() {
        String[] statusPadrao = {"ativo", "inativo"};

        try (Connection conn = conexao.getConexao()) {
            for (String status : statusPadrao) {
                if (!existeStatus(conn, status)) {  // Verifica se o status já existe no banco
                    String sql = "INSERT INTO status_aluno (nome) VALUES (?)";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, status);
                        ps.executeUpdate();  // Insere só se não existir
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Imprime exceções, bom para debug
        }
    }

    private boolean existeStatus(Connection conn, String nome) throws SQLException {
        String sql = "SELECT COUNT(*) FROM status_aluno WHERE nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Retorna true se já tem registro
                }
            }
        }
        return false;  // Se não achou, retorna false
    }
}
