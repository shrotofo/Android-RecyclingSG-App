package com.example.binbolehxfirebase;

public class Message {
    private String content;
    private boolean fromUser; // True if message is from user, false if from bot

    public Message(String content, boolean fromUser) {
        this.content = content;
        this.fromUser = fromUser;
    }
    // Getters
    public String getContent() {
        return content;
    }
    public boolean isFromUser() {
        return fromUser;
    }
}
