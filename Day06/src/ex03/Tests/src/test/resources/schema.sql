CREATE TABLE IF NOT EXISTS product
(
    id    integer NOT NULL,
    name  varchar(80),
    price numeric(12, 2),
    PRIMARY KEY (id)
);