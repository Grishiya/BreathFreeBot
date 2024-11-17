package com.grishiya.BreathFreeBot.botResponse.command;

import com.grishiya.BreathFreeBot.botResponse.service.KeyboardService;
import com.grishiya.BreathFreeBot.botResponse.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final MessageService messageService;
    private final KeyboardService keyboardService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        String welcomeText = "Добро пожаловать в бот 'Свободный Вдох'!" +
                " Я помогу вам на пути к здоровой и свободной от никотина жизни.";
        var keyboard = keyboardService.createQuitSmokingKeyboard();
        var message = messageService.createTextMessage(chatId, welcomeText);
        message.setReplyMarkup(keyboard);
        messageService.sendMessage(message);
    }
}
