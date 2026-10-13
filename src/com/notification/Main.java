package com.notification;

import com.notification.model.*;
import com.notification.sender.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== MULTI-CHANNEL NOTIFICATION GATEWAY DEMO ===\n");

        // Инициализируем провайдеры
        TelegramMessageSender telegram = new TelegramMessageSender();
        EmailMessageSender email = new EmailMessageSender();
        SmsMessageSender sms = new SmsMessageSender();
        PushNotificationSender push = new PushNotificationSender();

        // Сценарий 1: UrgentNotification + Telegram
        System.out.println("--- Scenario 1: UrgentNotification + Telegram ---");
        Notification urgent = new UrgentNotification(telegram);
        urgent.send("@user_john", "Server system crash detected!");

        // Сценарий 2: EncryptedNotification + Email
        System.out.println("\n--- Scenario 2: EncryptedNotification + Email ---");
        Notification encrypted = new EncryptedNotification(email);
        encrypted.send("alice@company.com", "Confidential Salary Report 2026");

        // Сценарий 3: ScheduledBatchNotification + SMS
        System.out.println("\n--- Scenario 3: ScheduledBatchNotification + SMS ---");
        ScheduledBatchNotification batch = new ScheduledBatchNotification(sms);
        batch.sendBatch(List.of("+123456789", "+987654321", "+1122334455"), "Your monthly billing statement is ready.");

        // Сценарий 4: Runtime switching: Telegram -> Email
        System.out.println("--- Scenario 4: Runtime Switching (Telegram -> Email) ---");
        Notification dynamicNotif = new UrgentNotification(telegram);
        System.out.print("Before switch: ");
        dynamicNotif.send("@dev_team", "Database low memory");

        dynamicNotif.setSender(email);
        System.out.print("After switch: ");
        dynamicNotif.send("dev_team@company.com", "Database low memory");

        // Сценарий 5: Telegram unavailable -> Fallback to SMS
        System.out.println("\n--- Scenario 5: Fallback Mechanism (Telegram Unavailable -> SMS) ---");
        telegram.setAvailable(false); // Имитируем сбой Telegram
        UrgentNotification fallbackNotif = new UrgentNotification(telegram, sms);
        fallbackNotif.send("+77015550102", "Critical Security Alert!");
        telegram.setAvailable(true); // Восстанавливаем Telegram

        // Сценарий 6: MarketingNotification + PushNotificationSender
        System.out.println("\n--- Scenario 6: New MarketingNotification + New PushNotificationSender ---");
        Notification marketing = new MarketingNotification(push);
        marketing.send("device_token_xyz_987", "Get 50% discount on your next order!");

        System.out.println("\n=== DEMO COMPLETED SUCCESSFULLY ===");
    }
}