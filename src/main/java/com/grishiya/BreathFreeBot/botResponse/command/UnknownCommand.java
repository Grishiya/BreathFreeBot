package com.grishiya.BreathFreeBot.botResponse.command;

import com.grishiya.BreathFreeBot.botResponse.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UnknownCommand implements Command {
    private final MessageService messageService;

    @Override
    public void execute(Update update) {
        Long chatId = update.hasMessage() ?
                update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String text = "Извините, я не понимаю эту команду.";
        var message = messageService.createTextMessage(chatId, toString());
        messageService.sendMessage(message);
    }
}
