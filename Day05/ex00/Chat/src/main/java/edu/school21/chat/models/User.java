package edu.school21.chat.models;

import java.util.List;

public class User {
    public static final String ENTITY_TABLE_NAME = "chat_user";
    private final Long id;
    private final String login;
    private final String password;
    private final List<Chatroom> createdRooms;
    private final List<Chatroom> usedRooms;

    public User(Long id, String login, String password,
                List<Chatroom> createdRooms, List<Chatroom> usedRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.usedRooms = usedRooms;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public List<Chatroom> getUsedRooms() {
        return usedRooms;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User other = (User) obj;
        return (id != null && id.equals(other.getId()))
                && (login != null && login.equals(other.getLogin()))
                && (password != null && password.equals(other.getPassword()))
                && (createdRooms != null
                && createdRooms.equals(other.getCreatedRooms()))
                && (usedRooms != null
                && usedRooms.equals(other.getUsedRooms()));
    }

    @Override
    public int hashCode() {
        final int FACTOR = 11;
        int result = 1;
        result = FACTOR * result + (id == null ? 0 : id.hashCode());
        result = FACTOR * result + (login == null ? 0 : login.hashCode());
        result = FACTOR * result + (password == null ? 0 : password.hashCode());
        result = FACTOR * result + (createdRooms == null ?
                0 : createdRooms.hashCode());
        result = FACTOR * result + (usedRooms == null ?
                0 : usedRooms.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", login = \"" + login + "\", password = \""
                + password + "\", createdRooms = " + createdRooms +
                "\", rooms = " + usedRooms + "}";
    }
}
