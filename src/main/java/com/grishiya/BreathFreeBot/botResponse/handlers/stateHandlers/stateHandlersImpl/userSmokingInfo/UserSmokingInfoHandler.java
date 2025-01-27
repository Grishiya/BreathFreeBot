package com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl.userSmokingInfo;

import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.StateHandler;
import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import com.grishiya.BreathFreeBot.botResponse.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSmokingInfoHandler implements StateHandler {
    private final MessageService messageService;

    @Override
    public void handle(Update update, UserEntity user) {
        if (update.hasCallbackQuery()) {
            handleCallback(update, user);
        }
    }

    private void handleCallback(Update update, UserEntity user) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();
        var messageId = callbackQuery.getMessage().getMessageId();
        long chatId = user.getChatId();
        if (data.equals("CONTINUE")) {
            durationMessage(user);
        }
    }

    private void durationMessage(UserEntity user) {
        String string = TextForSIH.textForDuration;
        InlineKeyboardMarkup keyboardMarkup = buildSmokingDurationKeyboard();
        messageService.sendMessageWithKeyboard(user.getChatId(), string, keyboardMarkup);

    }

    private InlineKeyboardMarkup buildSmokingDurationKeyboard() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        String s = "F";
        InlineKeyboardButton button = new InlineKeyboardButton("Меньше трех месяцев");
        button.setCallbackData("DURATION:0");
        InlineKeyboardButton button1 = new InlineKeyboardButton("от 3 месяцев до года");
        button1.setCallbackData("DURATION:1");
        InlineKeyboardButton button2 = new InlineKeyboardButton("От 1 года до 3 лет");
        button2.setCallbackData("DURATION:2");
        InlineKeyboardButton button3 = new InlineKeyboardButton("От 3 до 7 лет");
        button3.setCallbackData("DURATION:3");
        InlineKeyboardButton button4 = new InlineKeyboardButton("От 7 до 15 лет");
        button4.setCallbackData("DURATION:4");
        InlineKeyboardButton button5 = new InlineKeyboardButton("От 15 до 25 лет");
        button5.setCallbackData("DURATION:5");
        InlineKeyboardButton button6 = new InlineKeyboardButton("От 25 до 40 лет");
        button6.setCallbackData("DURATION:6");
        InlineKeyboardButton button7 = new InlineKeyboardButton("Больше 40 лет");
        button7.setCallbackData("DURATION:7");
        rows.add(Collections.singletonList(button));
        rows.add(Collections.singletonList(button1));
        rows.add(Collections.singletonList(button2));
        rows.add(Collections.singletonList(button3));
        rows.add(Collections.singletonList(button4));
        rows.add(Collections.singletonList(button5));
        rows.add(Collections.singletonList(button6));
        rows.add(Collections.singletonList(button7));
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
}