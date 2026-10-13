package com.notification.model;

import com.notification.sender.MessageSender;

public class MarketingNotification extends Notification {

    public MarketingNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(String recipient, String message) {
        String promoMessage = "🔥 SPECIAL OFFER 🔥: " + message + " | Unsubscribe link: https://promo.unsubscribe";
        sender.sendMessage(recipient, "Special Promo For You!", promoMessage);
    }
}