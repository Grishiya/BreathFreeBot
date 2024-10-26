package controller;

import botResponse.BotResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BrosatorTgBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private  String botName;
    @Value("${bot.token}")
    private  String botToken;
    @Autowired
    BotResponses botResponses;


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chatId = update.getMessage().getChatId();
            var messageText = update.getMessage().getText();
            if (messageText.equals("/start")) {
                sendMessage(chatId, botResponses.getWelcomeMessage());
            } else if (messageText.equals("Бросить курить")) {
                sendMessage(chatId, botResponses.getStartQuitMessage());
            }
        }
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

    @Override
    public String getBotToken() {
        return this.botToken;
    }
    @Override
    public String getBotUsername() {
        return this.botName;
    }
}
