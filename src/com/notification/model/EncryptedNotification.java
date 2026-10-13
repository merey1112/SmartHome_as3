package com.notification.model;

import com.notification.sender.MessageSender;
import java.util.Base64;

public class EncryptedNotification extends Notification {

    public EncryptedNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(String recipient, String message) {
        String encryptedMessage = encrypt(message);
        sender.sendMessage(recipient, "Encrypted Message", encryptedMessage);
    }

    private String encrypt(String rawText) {
        return Base64.getEncoder().encodeToString(rawText.getBytes());
    }
}