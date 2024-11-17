package com.grishiya.BreathFreeBot.botResponse.command;

import com.grishiya.BreathFreeBot.botResponse.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class QuitSmockingCommand implements Command {
    private final MessageService messageService;

    @Override
    public void execute(Update update) {
        Long chatId;
        chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String text = "Отличное решение! Давайте начнём ваш путь к здоровью.";
        var message = messageService.createTextMessage(chatId, text);
        messageService.sendMessage(message);
    }

}
