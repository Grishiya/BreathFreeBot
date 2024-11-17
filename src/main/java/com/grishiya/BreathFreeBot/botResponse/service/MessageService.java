package com.grishiya.BreathFreeBot.botResponse.service;

import com.grishiya.BreathFreeBot.botResponse.controller.BrosatorTgBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final BrosatorTgBot brosatorTgBot;

    public void sendMessage(SendMessage sendMessage) {
        try {
            brosatorTgBot.execute(sendMessage);
            log.info("Сообщение отправлено пользователю {}", sendMessage.getChatId());
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения пользователю {}: {}", sendMessage.getChatId(), e.getMessage());
        }
    }

    public SendMessage createTextMessage(Long chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();
    }
}
