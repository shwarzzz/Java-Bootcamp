DROP TABLE IF EXISTS "user";
CREATE TABLE IF NOT EXISTS "user"
(
    id       serial,
    name     varchar(80) UNIQUE,
    password text,
    PRIMARY KEY (id)
);