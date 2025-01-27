package com.grishiya.BreathFreeBot.botResponse.handlers;

import com.grishiya.BreathFreeBot.botResponse.services.MessageService;
import com.grishiya.BreathFreeBot.botResponse.services.UserService;
import com.grishiya.BreathFreeBot.botResponse.services.begin.BeginPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextMessageHandler {
    private final UserService userService;
    private final MessageService messageService;
    private final BeginPageService beginPageService;
    public void handleText(Long chatId, String text) throws IOException {
        var optUser = userService.findByChatId(chatId);
        if (optUser.isEmpty()) {
            userService.findOrCreateUser(chatId, text);

            messageService.createPhotoMessageWithCaptionAndButton(chatId, beginPageService.getPage(), null);
        } else {
            messageService.sendTextMessage(chatId,"Что?");
        }
    }
}
