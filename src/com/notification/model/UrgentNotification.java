package com.notification.model;

import com.notification.sender.MessageSender;

public class UrgentNotification extends Notification {
    private MessageSender fallbackSender;

    public UrgentNotification(MessageSender sender) {
        super(sender);
    }

    public UrgentNotification(MessageSender sender, MessageSender fallbackSender) {
        super(sender);
        this.fallbackSender = fallbackSender;
    }

    public void setFallbackSender(MessageSender fallbackSender) {
        this.fallbackSender = fallbackSender;
    }

    @Override
    public void send(String recipient, String message) {
        String urgentMessage = "[URGENT] " + message;

        if (sender.isAvailable()) {
            sender.sendMessage(recipient, "URGENT ALERT", urgentMessage);
        } else if (fallbackSender != null && fallbackSender.isAvailable()) {
            System.out.println("[Gateway] Primary sender (" + sender.getChannelName() + ") unavailable. Switching to Fallback (" + fallbackSender.getChannelName() + ")...");
            fallbackSender.sendMessage(recipient, "URGENT ALERT", urgentMessage);
        } else {
            System.out.println("[Gateway] Failed to send URGENT message: All channels unavailable!");
        }
    }
}