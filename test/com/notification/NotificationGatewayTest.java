package com.notification;

import com.notification.model.*;
import com.notification.sender.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationGatewayTest {

    private TelegramMessageSender telegram;
    private EmailMessageSender email;
    private SmsMessageSender sms;
    private PushNotificationSender push;

    @BeforeEach
    void setUp() {
        telegram = new TelegramMessageSender();
        email = new EmailMessageSender();
        sms = new SmsMessageSender(160);
        push = new PushNotificationSender();
    }

    @Test
    void testSuccessfulDelivery() {
        assertTrue(telegram.sendMessage("john", "Test", "Hello"));
    }

    @Test
    void testUnavailableProvider() {
        telegram.setAvailable(false);
        assertFalse(telegram.sendMessage("john", "Test", "Hello"));
    }

    @Test
    void testFallbackMechanismWhenPrimaryFails() {
        telegram.setAvailable(false);
        UrgentNotification notification = new UrgentNotification(telegram, sms);
        assertDoesNotThrow(() -> notification.send("+77011112233", "System Alert"));
    }

    @Test
    void testFallbackFailsWhenAllSendersUnavailable() {
        telegram.setAvailable(false);
        sms.setAvailable(false);
        UrgentNotification notification = new UrgentNotification(telegram, sms);
        assertDoesNotThrow(() -> notification.send("+77011112233", "System Alert"));
    }

    @Test
    void testSmsCharacterRestriction() {
        SmsMessageSender strictSms = new SmsMessageSender(10);
        assertTrue(strictSms.sendMessage("123", "", "Very Long Text Message"));
    }

    @Test
    void testEncryptedNotification() {
        EncryptedNotification encryptedNotif = new EncryptedNotification(email);
        assertDoesNotThrow(() -> encryptedNotif.send("test@mail.com", "Secret"));
    }

    @Test
    void testScheduledBatchNotification() {
        ScheduledBatchNotification batchNotif = new ScheduledBatchNotification(sms);
        List<String> recipients = List.of("User1", "User2", "User3");
        assertDoesNotThrow(() -> batchNotif.sendBatch(recipients, "Batch Message"));
    }

    @Test
    void testRuntimeSenderSwitching() {
        Notification notification = new UrgentNotification(telegram);
        assertEquals("Telegram", notification.getSender().getChannelName());

        notification.setSender(email);
        assertEquals("Email", notification.getSender().getChannelName());
    }

    @Test
    void testNewPushNotificationSender() {
        Notification notification = new UrgentNotification(push);
        assertEquals("PushNotification", notification.getSender().getChannelName());
        assertTrue(push.sendMessage("device_123", "Title", "Body"));
    }

    @Test
    void testNewMarketingNotification() {
        MarketingNotification marketing = new MarketingNotification(push);
        assertDoesNotThrow(() -> marketing.send("device_456", "Get 30% Off!"));
    }

    @Test
    void testKafkaEventSender() {
        KafkaEventSender kafka = new KafkaEventSender();
        assertTrue(kafka.sendMessage("topic_key", "Event", "Payload"));
    }
}