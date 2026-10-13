package com.notification.sender;

public class TelegramMessageSender implements MessageSender {
    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("[Telegram] Error: Channel is unavailable for " + recipient);
            return false;
        }
        System.out.println("[Telegram] Sending to " + recipient + " | *" + title + "* \n>>> " + body);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Telegram";
    }
}
