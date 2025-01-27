package com.grishiya.BreathFreeBot.botResponse.controller;

import com.grishiya.BreathFreeBot.botResponse.handlers.UpdateHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@Slf4j
public class TgBot2 extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    private final UpdateHandler updateHandler;
    public TgBot2 (UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }




    @Override
    public void onUpdateReceived(Update update) {
        try {
            updateHandler.updateHandler(update);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

}
