package com.grishiya.BreathFreeBot.botResponse.handlers;

import com.grishiya.BreathFreeBot.botResponse.dispatcher.StateDispatcher;
import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl.NewStateHandlers;
import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import com.grishiya.BreathFreeBot.botResponse.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateHandler {
    private final CommandHandler commandHandler;
    private final TextMessageHandler textMessageHandler;
    private final UserService userService;
    private final StateDispatcher stateDispatcher;
    private final NewStateHandlers newStateHandlers;
    public void updateHandler(Update update) throws Exception {
        Long chatId = extractChatId(update);
        var optUser = userService.findByChatId(chatId);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if (text.equals("/start")) {
                if (optUser.isEmpty()) {
                    UserEntity user = userService.createUser(chatId);
                    newStateHandlers.startWelcomeFlow(user);
                    stateDispatcher.handleExistingUser(update, user);
                } else {
                    UserEntity user = optUser.get();
                    stateDispatcher.handleExistingUser(update, user);
                }
            }else {
                if (optUser.isPresent()) {
                    UserEntity user = optUser.get();
                    stateDispatcher.handleExistingUser(update, user);
                }
            }
        } else
         if (update.hasCallbackQuery()) {
            if (optUser.isPresent()) {
                UserEntity user = optUser.get();
                stateDispatcher.handleExistingUser(update, user);
            }else {
            }

        } else {
            log.warn("Неизвестный тип сообщения или не обрабатываемое событие");
        }
    }
    private Long extractChatId(Update update) {
        if (update.hasMessage()) return update.getMessage().getChatId();
        if (update.hasCallbackQuery()) return update.getCallbackQuery().getMessage().getChatId();
        return null;
    }

}
