package com.notification.sender;

public class EmailMessageSender implements MessageSender {
    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("[Email] Error: Server unavailable for " + recipient);
            return false;
        }
        System.out.println("[Email] To: " + recipient + " | Subject: " + title);
        System.out.println("Body: " + body);
        System.out.println("Signature: -- Sent automatically via Notification Gateway --");
        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Email";
    }
}