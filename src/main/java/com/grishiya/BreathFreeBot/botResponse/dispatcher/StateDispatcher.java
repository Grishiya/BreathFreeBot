package com.grishiya.BreathFreeBot.botResponse.dispatcher;

import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl.Begin.BeginStateHandler;
import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl.NewStateHandlers;
import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl.WaitingNameHandler;
import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl.userSmokingInfo.UserSmokingInfoHandler;
import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import com.grishiya.BreathFreeBot.botResponse.services.MessageService;
import com.grishiya.BreathFreeBot.botResponse.services.begin.BeginPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StateDispatcher {
    private final NewStateHandlers newStateHandlers;
    private final MessageService messageService;
    private final WaitingNameHandler waitingNameHandler;
    private final BeginStateHandler beginStateHandler;
    private final UserSmokingInfoHandler smokingInfoHandler;
    public void handleExistingUser(Update update, UserEntity user) throws IOException {
        switch (user.getState()) {
            case NEW -> newStateHandlers.handle(update, user); // Продолжение приветствия
            case WAITING_NAME -> waitingNameHandler.handle(update, user);
            case BEGIN -> beginStateHandler.handle(update, user);
            case USER_SMOKING_INFO -> smokingInfoHandler.handle(update, user);
//        case MAIN_MENU -> messageService.sendText(user.getChatId(), "Вы уже в главном меню!");
            default -> messageService.sendTextMessage(user.getChatId(), "Неизвестное состояние!");

        }
    }
}
