package DAO;


import conexao.conexao;
import entity.FichaTreino;
import entity.Aluno;

import java.sql.*;
import java.util.List;

public class FichaTreinoDao {

    private final Connection conn;

    public FichaTreinoDao() {
        this.conn = conexao.getConexao();
    }

    public void salvar(FichaTreino ficha, Aluno aluno) throws SQLException {
        // Assumindo que ficha.getPlano() já tem o idBanco preenchido
        String sqlFicha = "INSERT INTO fichas_treino (plano_id) VALUES (?)";
        String sqlExercicioBusca = "SELECT id FROM exercicio WHERE nome = ?";
        String sqlExercicioInsere = "INSERT INTO exercicio (nome) VALUES (?)";
        String sqlCadastro = "INSERT INTO cadastro (aluno_id, fichas_treino_id, exercicio_id) VALUES (?, ?, ?)";

        try {
            conn.setAutoCommit(false);

            // Salvar ficha_treino e pegar ID gerado
            int fichaId;
            try (PreparedStatement psFicha = conn.prepareStatement(sqlFicha, Statement.RETURN_GENERATED_KEYS)) {
                psFicha.setInt(1, ficha.getPlano().getIdBanco());
                psFicha.executeUpdate();

                try (ResultSet rs = psFicha.getGeneratedKeys()) {
                    if (rs.next()) {
                        fichaId = rs.getInt(1);
                        ficha.setIdBanco(fichaId);
                    } else {
                        throw new SQLException("Falha ao obter id da ficha de treino.");
                    }
                }
            }

            // Para cada exercício da ficha, verificar se existe na tabela exercicio, se não inserir, e obter id
            for (String nomeExercicio : ficha.getExercicios()) {
                int exercicioId = -1;

                // Buscar exercício
                try (PreparedStatement psBusca = conn.prepareStatement(sqlExercicioBusca)) {
                    psBusca.setString(1, nomeExercicio);
                    try (ResultSet rsBusca = psBusca.executeQuery()) {
                        if (rsBusca.next()) {
                            exercicioId = rsBusca.getInt("id");
                        }
                    }
                }

                // Se não existe, inserir
                if (exercicioId == -1) {
                    try (PreparedStatement psInsere = conn.prepareStatement(sqlExercicioInsere, Statement.RETURN_GENERATED_KEYS)) {
                        psInsere.setString(1, nomeExercicio);
                        psInsere.executeUpdate();

                        try (ResultSet rsInsere = psInsere.getGeneratedKeys()) {
                            if (rsInsere.next()) {
                                exercicioId = rsInsere.getInt(1);
                            } else {
                                throw new SQLException("Falha ao inserir exercício.");
                            }
                        }
                    }
                }

                // Inserir vínculo na tabela cadastro
                try (PreparedStatement psCadastro = conn.prepareStatement(sqlCadastro)) {
                    psCadastro.setInt(1, aluno.getIdBanco());
                    psCadastro.setInt(2, fichaId);
                    psCadastro.setInt(3, exercicioId);
                    psCadastro.executeUpdate();
                }
            }

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
