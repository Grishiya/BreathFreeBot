package com.grishiya.BreathFreeBot.botResponse.controller;

import com.grishiya.BreathFreeBot.botResponse.entity.model.User;
import com.grishiya.BreathFreeBot.botResponse.handler.CommandHandler;
import com.grishiya.BreathFreeBot.botResponse.responses.BotResponses;
import com.grishiya.BreathFreeBot.botResponse.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Slf4j
@RequiredArgsConstructor
public class BrosatorTgBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    private final UserService userService;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                handleIncomingMessage(update.getMessage());
            } else if (update.hasCallbackQuery()) {
                handleCallbackQuery(update.getCallbackQuery());
            }
        } catch (Exception e) {
            log.error("Ошибка при обработке обновления: {}", e.getMessage());
        }
    }

    private void handleIncomingMessage(Message message) throws TelegramApiException {
        Long chatId = message.getChatId();
        String text = message.getText();

        // Получаем или создаем пользователя
        User user = userService.findByChatId(chatId);
        if (user == null) {
            user = new User();
            user.setChatId(chatId);
            userService.saveUser(user);
        }

        String userState = user.getState();

        if ("/start".equals(text)) {
            sendInitialMessage(chatId);
            user.setState(null);
            userService.saveUser(user);
        } else if ("AWAITING_NAME".equals(userState)) {
            // Бот ожидает имя пользователя
            user.setName(text.trim());
            user.setRegistrationTime(LocalDateTime.now());
            user.setState(null);
            userService.saveUser(user);

            sendPersonalizedWelcomeMessage(chatId, user.getName());
        } else {
            sendUnknownCommandMessage(chatId);
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        Long chatId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();

        // Получаем или создаем пользователя
        User user = userService.findByChatId(chatId);
        if (user == null) {
            user = new User();
            user.setChatId(chatId);
            userService.saveUser(user);
        }

        if ("quit_smoking".equals(data)) {
            askForUserName(chatId);
            user.setState("AWAITING_NAME");
            userService.saveUser(user);
        } else if ("button_1".equals(data)) {
            sendMessage(chatId, "Вы нажали кнопку 1.");
        } else if ("button_2".equals(data)) {
            sendMessage(chatId, "Вы нажали кнопку 2.");
        } else if ("button_3".equals(data)) {
            sendMessage(chatId, "Вы нажали кнопку 3.");
        } else {
            sendUnknownCommandMessage(chatId);
        }
    }

    private void sendInitialMessage(Long chatId) throws TelegramApiException {
        String text = "Добро пожаловать! Я помогу тебе бросить курить.";

        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text("🚭 Бросить курить")
                                .callbackData("quit_smoking")
                                .build()
                ))
                .build();

        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();

        execute(message);
    }

    private void askForUserName(Long chatId) throws TelegramApiException {
        String text = "Пожалуйста, введите ваше имя.";

        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();

        execute(message);
    }

    private void sendPersonalizedWelcomeMessage(Long chatId, String name) throws TelegramApiException {
        String text = "Я очень рад, " + name + ", ты идёшь в правильном направлении.";

        // Создаем клавиатуру с кнопками 1, 2, 3
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text("1")
                                .callbackData("button_1")
                                .build(),
                        InlineKeyboardButton.builder()
                                .text("2")
                                .callbackData("button_2")
                                .build(),
                        InlineKeyboardButton.builder()
                                .text("3")
                                .callbackData("button_3")
                                .build()
                ))
                .build();

        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();

        execute(message);
    }

    private void sendMessage(Long chatId, String text) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();

        execute(message);
    }

    private void sendUnknownCommandMessage(Long chatId) throws TelegramApiException {
        String text = "Извините, я не понимаю эту команду. Используйте /start для начала.";

        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();

        execute(message);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}