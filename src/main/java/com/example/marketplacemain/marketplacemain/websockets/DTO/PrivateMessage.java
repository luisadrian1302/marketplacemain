package com.example.marketplacemain.marketplacemain.websockets.DTO;

public class PrivateMessage {
    private String recipient; // Destinatario único (usuario objetivo)
    private String content;   // Contenido del mensaje

    // Getters y setters
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
