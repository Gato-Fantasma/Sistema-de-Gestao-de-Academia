--criar banco
CREATE DATABASE banco;

-- usar banco
USE banco;

--criar tabelas
CREATE TABLE status_aluno (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);

CREATE TABLE endereco (
    id INT PRIMARY KEY AUTO_INCREMENT,
    rua VARCHAR(100),
    numero VARCHAR(10),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(2),
    cep VARCHAR(10)
);-- ja feito no java o insert

CREATE TABLE alunos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    status_aluno_id INT,
    endereco_id INT,
    FOREIGN KEY (status_aluno_id) REFERENCES status_aluno(id),
    FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

CREATE TABLE plano (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(50)
);-- ja feito no java o insert

CREATE TABLE fichas_treino (
    id INT PRIMARY KEY AUTO_INCREMENT,
    plano_id INT,
    FOREIGN KEY (plano_id) REFERENCES plano(id)
);-- ja feito no java o insert

CREATE TABLE exercicio (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);

CREATE TABLE cadastro (
    aluno_id INT,
    fichas_treino_id INT,
    exercicio_id INT,
    PRIMARY KEY (aluno_id, fichas_treino_id, exercicio_id),
    FOREIGN KEY (aluno_id) REFERENCES alunos(id),
    FOREIGN KEY (fichas_treino_id) REFERENCES fichas_treino(id),
    FOREIGN KEY (exercicio_id) REFERENCES exercicio(id)
);

