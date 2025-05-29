package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.conexao;
import entity.Aluno;
import entity.Endereco;

public class AlunoDao {

    public void salvar(Aluno aluno) {
        String sqlEndereco = "INSERT INTO endereco (rua, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlBuscaStatus = "SELECT id FROM status_aluno WHERE nome = ?";
        String sqlAluno = "INSERT INTO alunos (nome, status_aluno_id, endereco_id) VALUES (?, ?, ?)";
        String sqlVerificaExistencia = "SELECT COUNT(*) FROM alunos WHERE nome = ?";

        try (Connection conn = conexao.getConexao()) {
            conn.setAutoCommit(false); // Início da transação

            // Verifica se o aluno já existe pelo nome
            try (PreparedStatement psVerifica = conn.prepareStatement(sqlVerificaExistencia)) {
                psVerifica.setString(1, aluno.getNome());
                try (ResultSet rs = psVerifica.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Aluno já cadastrado!");
                        conn.rollback();
                        return;
                    }
                }
            }

            // Salva endereço
            int enderecoId;
            try (PreparedStatement psEndereco = conn.prepareStatement(sqlEndereco, PreparedStatement.RETURN_GENERATED_KEYS)) {
                Endereco endereco = aluno.getEndereco();
                psEndereco.setString(1, endereco.getRua());
                psEndereco.setString(2, endereco.getNumero());
                psEndereco.setString(3, endereco.getBairro());
                psEndereco.setString(4, endereco.getCidade());
                psEndereco.setString(5, endereco.getEstado());
                psEndereco.setString(6, endereco.getCep());

                psEndereco.executeUpdate();

                try (ResultSet rs = psEndereco.getGeneratedKeys()) {
                    if (rs.next()) {
                        enderecoId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        throw new SQLException("Falha ao obter ID do endereço.");
                    }
                }
            }

            // Busca status ID
            int statusId;
            try (PreparedStatement psBuscaStatus = conn.prepareStatement(sqlBuscaStatus)) {
                psBuscaStatus.setString(1, aluno.getStatus().getNome());
                try (ResultSet rs = psBuscaStatus.executeQuery()) {
                    if (rs.next()) {
                        statusId = rs.getInt("id");
                    } else {
                        conn.rollback();
                        throw new SQLException("Status do aluno não encontrado: " + aluno.getStatus().getNome());
                    }
                }
            }

            // Salva aluno
            try (PreparedStatement psAluno = conn.prepareStatement(sqlAluno, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psAluno.setString(1, aluno.getNome());
                psAluno.setInt(2, statusId);
                psAluno.setInt(3, enderecoId);

                psAluno.executeUpdate();

                try (ResultSet rs = psAluno.getGeneratedKeys()) {
                    if (rs.next()) {
                        aluno.setIdBanco(rs.getInt(1));
                    } else {
                        conn.rollback();
                        throw new SQLException("Falha ao obter ID do aluno.");
                    }
                }
            }

            conn.commit(); // Finaliza transação

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
