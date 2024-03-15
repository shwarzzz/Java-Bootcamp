package edu.school21.chat.models;

import java.sql.Timestamp;

public class Message {
    public static final String ENTITY_TABLE_NAME = "message";
    private final Long id;
    private final User author;
    private final Chatroom chatroom;
    private final String text;
    private final Timestamp date;

    public Message(Long id, User author, Chatroom chatroom,
                   String text, Timestamp date) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public String getText() {
        return text;
    }

    public Timestamp getDate() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Message other = (Message) obj;
        return (id != null && id.equals(other.getId()))
                && (author != null && author.equals(other.getAuthor()))
                && (chatroom != null && chatroom.equals(other.getChatroom()))
                && (text != null && text.equals(other.getText()))
                && (date != null && date.equals(other.getDate()));
    }

    @Override
    public int hashCode() {
        final int FACTOR = 17;
        int result = 1;
        result = FACTOR * result + (id == null ? 0 : id.hashCode());
        result = FACTOR * result + (author == null ? 0 : author.hashCode());
        result = FACTOR * result + (chatroom == null ? 0 : chatroom.hashCode());
        result = FACTOR * result + (text == null ? 0 : text.hashCode());
        result = FACTOR * result + (date == null ? 0 : date.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", author = " + author + ", room = " + chatroom +
                ", text = \"" + text + "\", dateTime = " + date + "}";
    }
}
