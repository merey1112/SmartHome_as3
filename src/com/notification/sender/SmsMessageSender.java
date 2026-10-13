package com.notification.sender;

public class SmsMessageSender implements MessageSender {
    private boolean available = true;
    private int maxCharacters = 160;

    public SmsMessageSender() {}

    public SmsMessageSender(int maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("[SMS] Error: Network offline for " + recipient);
            return false;
        }
        String fullText = title.isEmpty() ? body : title + ": " + body;
        if (fullText.length() > maxCharacters) {
            System.out.println("[SMS] Warning: Message trimmed to " + maxCharacters + " chars.");
            fullText = fullText.substring(0, maxCharacters);
        }
        System.out.println("[SMS] Sending to " + recipient + ": " + fullText);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}