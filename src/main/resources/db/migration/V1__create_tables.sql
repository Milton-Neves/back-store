CREATE TABLE produto (
                          id serial PRIMARY KEY,
                          nome varchar(255) NOT NULL,
                          preco numeric(10,2) NOT NULL,
                          quantidade integer NOT NULL,
                          imagem text
);