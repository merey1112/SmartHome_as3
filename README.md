# Assignment 3 — Multi-Channel Notification Gateway (Bridge Pattern)

## Overview
This project implements a Multi-Channel Notification Gateway using the **Bridge Pattern** in Java 17+.
The system separates **Notification Types** (Abstraction) from **Delivery Providers** (Implementation), allowing them to evolve independently.

---

## ❓ Architectural Q&A (Section 9)

### 1. What are the two independent dimensions?
- **Abstraction Dimension (Notification Types):** Handles notification logic and message behavior (e.g., `UrgentNotification`, `EncryptedNotification`, `ScheduledBatchNotification`, `MarketingNotification`).
- **Implementation Dimension (Delivery Providers):** Handles channel-specific message delivery mechanics (e.g., `TelegramMessageSender`, `EmailMessageSender`, `SmsMessageSender`, `KafkaEventSender`, `PushNotificationSender`).

### 2. Where exactly is the Bridge?
The Bridge is the `protected MessageSender sender;` reference field inside the abstract `Notification` class, coupled with `public void setSender(MessageSender sender)`.
This composition relationship (`Notification HAS-A MessageSender`) links the Abstraction hierarchy to the Implementor hierarchy.

### 3. Why is inheritance problematic here?
Without Bridge, combining $N$ notification types with $M$ providers via inheritance requires $N \times M$ concrete classes (e.g., `UrgentTelegramNotification`, `EncryptedSmsNotification`). Adding new types or providers leads to class explosion and tight coupling[cite: 1].

### 4. What happens when a new provider is added?
When adding a new provider (e.g., `PushNotificationSender`), we only implement the `MessageSender` interface[cite: 1]. Zero existing `Notification` classes are modified[cite: 1].

### 5. What happens when a new Notification type is added?
When adding a new notification type (e.g., `MarketingNotification`), we only extend the `Notification` abstract class[cite: 1]. Zero existing `MessageSender` provider classes are modified[cite: 1].

### 6. Why is this Bridge rather than Adapter?
- **Bridge** is designed *upfront* during architecture creation to let abstractions and implementations vary independently via composition[cite: 1].
- **Adapter** is applied *retrospectively* to fix interface incompatibilities between existing, unmodifiable systems.

---

## 🛠️ Project Structure
```text
SmartHome_as3/
├── src/
│   └── com/
│       └── notification/
│           ├── model/                       # Abstraction Hierarchy
│           │   ├── Notification.java        # Base Abstraction
│           │   ├── UrgentNotification.java  # Refined Abstraction (Fallback)
│           │   ├── EncryptedNotification.java
│           │   ├── ScheduledBatchNotification.java
│           │   └── MarketingNotification.java
│           ├── sender/                      # Implementor Hierarchy
│           │   ├── MessageSender.java       # Implementor Interface
│           │   ├── TelegramMessageSender.java
│           │   ├── EmailMessageSender.java
│           │   ├── SmsMessageSender.java
│           │   ├── KafkaEventSender.java
│           │   └── PushNotificationSender.java
│           └── Main.java                    # Demo execution
├── test/
│   └── com/
│       └── notification/
│           └── NotificationGatewayTest.java # 11 JUnit 5 Tests
├── UML_Diagram.png                          # Class Diagram
└── README.md                                # Architectural Documentation