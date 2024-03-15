DROP TABLE IF EXISTS "user", message;
CREATE TABLE IF NOT EXISTS "user"
(
    id       serial,
    name     varchar(80) UNIQUE,
    password text,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS message
(
    id           serial,
    sender       varchar(80),
    message_text text,
    sending_time timestamp without time zone,
    PRIMARY KEY (id),
    CONSTRAINT fk_message_sender FOREIGN KEY (sender) REFERENCES "user" (name)
);