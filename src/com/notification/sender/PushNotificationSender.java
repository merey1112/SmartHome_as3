package com.notification.sender;

public class PushNotificationSender implements MessageSender {
    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("[Push] Error: Device token unreachable for " + recipient);
            return false;
        }
        System.out.println("[Push Notification] Device: " + recipient + " | " + title + " -> " + body);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "PushNotification";
    }
}