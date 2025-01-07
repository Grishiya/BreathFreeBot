package com.grishiya.BreathFreeBot.botResponse.services.start;

import com.grishiya.BreathFreeBot.botResponse.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class StartStateHandler implements StateHandler {
    private final UserService userService;
    private final MessageService messageService;

    public StartStateHandler(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @Override
    public void handleTextMessage(long chatId, String text) throws Exception {

    }

    @Override
    public void handleCallBackMessage(long chatId, String text) throws Exception {

    }
}
