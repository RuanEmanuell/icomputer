CREATE TABLE usuarios  (id_usuario SERIAL PRIMARY KEY, nome TEXT, email TEXT, senha TEXT, endereco TEXT, permissao_admin TEXT DEFAULT 'n');
CREATE TABLE vendas    (id_venda SERIAL PRIMARY KEY, id_usuario SERIAL, id_modelo SERIAL, data_venda DATE);
CREATE TABLE modelosPC (id_modelo SERIAL PRIMARY KEY, nome TEXT, cpu TEXT, ram INTEGER, ssd INTEGER, preco FLOAT);

INSERT INTO usuarios (nome, email, senha, endereco, permissao_admin) VALUES 
('admin', 'admin@gmail.com', '$2a$10$v30P8iwziPPj0rOCwz8iM.JQvsWRy3z42CRZ.KEX9b6rGbo3/VyJ2', 'Rua A, 123', 's');

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
(1, 1, '2024-01-10')