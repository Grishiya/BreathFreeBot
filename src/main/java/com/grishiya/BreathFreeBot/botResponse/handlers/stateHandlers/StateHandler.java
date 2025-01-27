package com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers;

import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface StateHandler {
    void handle(Update update, UserEntity user) throws IOException;
}
