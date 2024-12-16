CREATE TABLE usuarios  (id_usuario SERIAL PRIMARY KEY, nome TEXT, email TEXT, senha TEXT, endereco TEXT);
CREATE TABLE vendas    (id_venda SERIAL PRIMARY KEY, id_usuario SERIAL, id_modelo SERIAL, data_venda DATE);
CREATE TABLE modelosPC (id_modelo SERIAL PRIMARY KEY, nome TEXT, cpu TEXT, ram INTEGER, ssd INTEGER, preco FLOAT);

INSERT INTO usuarios (nome, email, senha, endereco) VALUES 
('João Silva', 'joao.silva@example.com', 'senha123', 'Rua A, 123'),
('Maria Oliveira', 'maria.oliveira@example.com', 'senha123', 'Rua B, 456'),
('Carlos Pereira', 'carlos.pereira@example.com', 'senha123', 'Rua C, 789'),
('Ana Souza', 'ana.souza@example.com', 'senha123', 'Rua D, 101'),
('Pedro Gomes', 'pedro.gomes@example.com', 'senha123', 'Rua E, 202'),
('Juliana Alves', 'juliana.alves@example.com', 'senha123', 'Rua F, 303'),
('Ricardo Lima', 'ricardo.lima@example.com', 'senha123', 'Rua G, 404'),
('Fernanda Costa', 'fernanda.costa@example.com', 'senha123', 'Rua H, 505'),
('Lucas Ferreira', 'lucas.ferreira@example.com', 'senha123', 'Rua I, 606'),
('Patrícia Martins', 'patricia.martins@example.com', 'senha123', 'Rua J, 707'),
('Gabriel Almeida', 'gabriel.almeida@example.com', 'senha123', 'Rua K, 808');

INSERT INTO modelosPC (nome, cpu, ram, ssd, preco) VALUES 
('PC Gamer Básico', 'Intel i3', 8, 256, 2500.00),
('PC Gamer Intermediário', 'Intel i5', 16, 512, 4500.00),
('PC Gamer Avançado', 'Intel i7', 32, 1024, 8500.00),
('Workstation', 'Intel Xeon', 64, 2048, 15000.00),
('Notebook Básico', 'AMD Ryzen 3', 8, 256, 2000.00),
('Notebook Intermediário', 'AMD Ryzen 5', 16, 512, 4000.00),
('Notebook Avançado', 'AMD Ryzen 7', 32, 1024, 8000.00),
('Mini PC', 'Intel Celeron', 4, 128, 1500.00),
('All-in-One', 'Intel i3', 8, 512, 3500.00),
('PC para Escritório', 'AMD Athlon', 4, 256, 1800.00),
('Servidor Básico', 'Intel Xeon', 128, 4096, 20000.00);

INSERT INTO vendas (id_usuario, id_modelo, data_venda) VALUES 
(1, 1, '2024-01-10'),
(2, 2, '2024-02-15'),
(3, 3, '2024-03-20'),
(4, 4, '2024-04-25'),
(5, 5, '2024-05-30'),
(6, 6, '2024-06-05'),
(7, 7, '2024-07-10'),
(8, 8, '2024-08-15'),
(9, 9, '2024-09-20'),
(10, 10, '2024-10-25'),
(11, 11, '2024-11-30');
