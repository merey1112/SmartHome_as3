package com.notification.model;

import com.notification.sender.MessageSender;
import java.util.List;

public class ScheduledBatchNotification extends Notification {

    public ScheduledBatchNotification(MessageSender sender) {
        super(sender);
    }

    public void sendBatch(List<String> recipients, String message) {
        int total = recipients.size();
        int successful = 0;
        int failed = 0;

        for (String recipient : recipients) {
            boolean status = sender.sendMessage(recipient, "Scheduled Batch Notification", message);
            if (status) {
                successful++;
            } else {
                failed++;
            }
        }

        System.out.println("\n--- Batch Sending Summary ---");
        System.out.println("Total: " + total);
        System.out.println("Successful: " + successful);
        System.out.println("Failed: " + failed);
        System.out.println("-----------------------------\n");
    }

    @Override
    public void send(String recipient, String message) {
        sendBatch(List.of(recipient), message);
    }
}