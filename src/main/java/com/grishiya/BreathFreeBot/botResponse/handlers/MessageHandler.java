package com.grishiya.BreathFreeBot.botResponse.handlers;

import com.grishiya.BreathFreeBot.botResponse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageHandler {
    @Autowired
    private UserService userService;
    @Autowired
    NameInputHandler nameInputHandler;
    @Autowired
    CommandHandler commandHandler;
    
}
