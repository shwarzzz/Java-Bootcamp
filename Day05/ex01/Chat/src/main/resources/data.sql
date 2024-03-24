INSERT INTO chat_user (login, password)
VALUES ('tomokoki', '123321'),
       ('bruscoaz', '4442321'),
       ('margartc', 'vfasf3332'),
       ('peachesl', 'fsdg22s./asd'),
       ('endeharh', 'bjr331.5Ab');

INSERT INTO chatroom (name, owner_id)
VALUES ('Java', (SELECT id FROM chat_user WHERE login = 'tomokoki')),
       ('DevOps', (SELECT id FROM chat_user WHERE login = 'bruscoaz')),
       ('C', (SELECT id FROM chat_user WHERE login = 'endeharh')),
       ('Python', (SELECT id FROM chat_user WHERE login = 'bruscoaz')),
       ('Golang', (SELECT id FROM chat_user WHERE login = 'margartc'));

INSERT INTO message (author_id, chatroom_id, text)
VALUES ((SELECT id FROM chat_user WHERE login = 'tomokoki'),
        (SELECT id FROM chatroom WHERE name = 'Java'),
        'Hello world!'),
       ((SELECT id FROM chat_user WHERE login = 'tomokoki'),
        (SELECT id FROM chatroom WHERE name = 'Golang'),
        'How are you?'),
       ((SELECT id FROM chat_user WHERE login = 'bruscoaz'),
        (SELECT id FROM chatroom WHERE name = 'DevOps'),
        'rm -rf ./ - it`s not a trap!'),
       ((SELECT id FROM chat_user WHERE login = 'endeharh'),
        (SELECT id FROM chatroom WHERE name = 'Java'),
        'good!'),
       ((SELECT id FROM chat_user WHERE login = 'peachesl'),
        (SELECT id FROM chatroom WHERE name = 'Golang'),
        'All right!');