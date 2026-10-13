package com.notification.sender;

public class KafkaEventSender implements MessageSender {
    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("[Kafka] Error: Cluster disconnected.");
            return false;
        }
        System.out.println("[Kafka] Publishing event to topic 'notifications' for key [" + recipient + "]: {" + title + " - " + body + "}");
        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Kafka";
    }
}