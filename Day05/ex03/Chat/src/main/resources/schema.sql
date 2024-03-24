CREATE TABLE IF NOT EXISTS chat_user
(
    id       serial      NOT NULL,
    login    varchar(30) NOT NULL,
    password varchar(30) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_chat_user_login UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS chatroom
(
    id       serial      NOT NULL,
    name     varchar(30) NOT NULL,
    owner_id integer     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_chatroom_owner_id FOREIGN KEY (owner_id) REFERENCES chat_user (id)
);

CREATE TABLE IF NOT EXISTS message
(
    id          serial  NOT NULL,
    author_id   integer NOT NULL,
    chatroom_id integer NOT NULL,
    text        varchar(4096),
    date        timestamp without time zone DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_message_user_id FOREIGN KEY (author_id) REFERENCES chat_user (id),
    CONSTRAINT fk_message_chatroom_id FOREIGN KEY (chatroom_id) REFERENCES chatroom (id)
);