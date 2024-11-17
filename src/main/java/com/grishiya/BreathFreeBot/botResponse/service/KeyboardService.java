package com.grishiya.BreathFreeBot.botResponse.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public class KeyboardService {
    public InlineKeyboardMarkup createQuitSmokingKeyboard() {
        InlineKeyboardButton quitButton = InlineKeyboardButton.builder()
                .text("🚭 Бросить курить")
                .callbackData("quit_smocking")
                .build();

        List<InlineKeyboardButton> keyboardRow = List.of(quitButton);
        List<List<InlineKeyboardButton>> keyboard = List.of(keyboardRow);

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }
}
