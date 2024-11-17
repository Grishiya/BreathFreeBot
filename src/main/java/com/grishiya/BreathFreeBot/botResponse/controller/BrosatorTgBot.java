package com.grishiya.BreathFreeBot.botResponse.controller;

import com.grishiya.BreathFreeBot.botResponse.responses.BotResponses;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Slf4j
public class BrosatorTgBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    @Autowired
    BotResponses botResponses;


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleIncomingMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void handleIncomingMessage(Update update) {
        var chatId = update.getMessage().getChatId();
        var messageText = update.getMessage().getText();
        log.info("Получено сообщение '{}' от пользователя {}", messageText, chatId);
        if (messageText.equals("/start")) {
            sendWelcomeMessage(chatId);

        } else if (messageText.equals("Бросить курить")) {
            sendMessage(chatId, botResponses.getStartQuitMessage());
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        Long chadId = callbackQuery.getMessage().getChatId();
        log.info("Получен CallbackQuery '{}' от пользователя {}", callbackData, chadId);
        if ("quit_smoking".equals(callbackData)) {
            sendQuitSmokingMessage(chadId);
        } else {
            sendUnkownCommandMessage(chadId);
        }
    }

    private void sendWelcomeMessage(Long chatId) {
        sendMessage(chatId, botResponses.getWelcomeMessage());
        SendMessage message = new SendMessage();
        message.setReplyMarkup(createQuitSmokingKeyboard());

    }
    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }
}
