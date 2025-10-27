package com.base.demo.Telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TelegramService {

    private final TelegramClient telegramClient;

    @Value("${telegram.bot-token}")
    private String botToken;

    @Value("${telegram.chat-id}")
    private String chatId;

    public TelegramService(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void sendNotification(String message) {
        try {
            telegramClient.sendMessage(botToken, chatId, message);
            System.out.println("✅ Telegram enviado: " + message);
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar Telegram: " + e.getMessage());
        }
    }
}
