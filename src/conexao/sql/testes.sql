--apagar o banco
DROP DATABASE banco;

--zerar todas as tabelas
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE cadastro;
TRUNCATE TABLE fichas_treino;
TRUNCATE TABLE exercicio;
TRUNCATE TABLE alunos;
TRUNCATE TABLE plano;
TRUNCATE TABLE endereco;
TRUNCATE TABLE status_aluno;

SET FOREIGN_KEY_CHECKS = 1;
-- para zerar é ate aqui