package edu.school21.chat.models;

import java.util.List;

public class Chatroom {
    public static final String ENTITY_TABLE_NAME = "chatroom";
    private final Long id;
    private final String name;
    private final User owner;
    private final List<Message> messages;

    public Chatroom(Long id, String name,
                    User owner, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Chatroom other = (Chatroom) obj;
        return (id != null && id.equals(other.getId()))
                && (name != null && name.equals(other.getName()))
                && (owner != null && owner.equals(other.getOwner()))
                && (messages != null && messages.equals(other.getMessages()));
    }

    @Override
    public int hashCode() {
        final int FACTOR = 7;
        int result = 1;
        result = result * FACTOR + (id == null ? 0 : id.hashCode());
        result = result * FACTOR + (name == null ? 0 : name.hashCode());
        result = result * FACTOR + (owner == null ? 0 : owner.hashCode());
        result = result * FACTOR + (messages == null ? 0 : messages.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", name = \"" + name +
                "\", owner = " + owner + ", messages = " + messages + "}";
    }
}
