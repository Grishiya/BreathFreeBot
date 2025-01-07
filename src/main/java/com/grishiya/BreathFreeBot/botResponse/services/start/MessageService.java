package com.grishiya.BreathFreeBot.botResponse.services.start;

import org.telegram.telegrambots.meta.api.methods.send.SendMediaBotMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public interface MessageService {
    public SendPhoto createPhotoMessageWithCaptionAndButton(
            Long chatId,
            String fileName,
            String caption,
            String buttonText,
            String callbackData) throws IOException;

}
