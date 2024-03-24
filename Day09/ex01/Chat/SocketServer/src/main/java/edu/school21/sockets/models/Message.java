package edu.school21.sockets.models;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.StringJoiner;

public class Message {
    public static final String TABLE_NAME = "message";

    private Long id;
    private String senderName;
    private String text;
    private Timestamp sendingTime;

    public Message(Long id, String senderName, String text, Timestamp sendingTime) {
        this.id = id;
        this.senderName = senderName;
        this.text = text;
        this.sendingTime = sendingTime;
    }

    public Message(String senderName, String text, Timestamp sendingTime) {
        this.senderName = senderName;
        this.text = text;
        this.sendingTime = sendingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Timestamp sendingTime) {
        this.sendingTime = sendingTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Message other = (Message) obj;
        return Objects.equals(id, other.getId())
                && Objects.equals(senderName, other.getSenderName())
                && Objects.equals(text, other.getText())
                && Objects.equals(sendingTime, other.getSendingTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderName, text, sendingTime);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Message.class.getSimpleName() + "{", "}")
                .add("id=" + id.toString())
                .add("sender=" + senderName)
                .add("text='" + text + "'")
                .add("sending time=" + sendingTime)
                .toString();
    }
}
