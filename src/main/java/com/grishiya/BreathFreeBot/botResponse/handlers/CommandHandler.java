package com.grishiya.BreathFreeBot.botResponse.handlers;

import com.grishiya.BreathFreeBot.botResponse.dispatcher.StateDispatcher;
import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl.NewStateHandlers;
import com.grishiya.BreathFreeBot.botResponse.keyboards.KeyboardForStartMessage;
import com.grishiya.BreathFreeBot.botResponse.model.Page;
import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import com.grishiya.BreathFreeBot.botResponse.services.MessageService;
import com.grishiya.BreathFreeBot.botResponse.services.UserService;
import com.grishiya.BreathFreeBot.botResponse.services.start.WelcomePageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandHandler {
    private final UserService userService;
    private final WelcomePageService welcomePageService;
    private final MessageService messageService;
    private final NewStateHandlers newStateHandlers;
    private final StateDispatcher stateDispatcher;
    public void handleStart(Long chatId) throws IOException {
        Optional<UserEntity> optUser = userService.findByChatId(chatId);
        if (optUser.isEmpty()) {
            UserEntity user= userService.createUser(chatId);
            newStateHandlers.startWelcomeFlow(user);
        }
    }
    public void sendPage(Long chatId, int pageIndex) throws IOException {
        Page page = welcomePageService.getPage(pageIndex);
        InlineKeyboardMarkup keyboardMarkup = KeyboardForStartMessage.buildKeyboardForStartMessage(pageIndex,
                welcomePageService.getTotalPages());
        messageService.createPhotoMessageWithCaptionAndButton(chatId, page, keyboardMarkup);
    }
}
