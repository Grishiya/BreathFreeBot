package com.grishiya.BreathFreeBot.botResponse.services.start;

public interface StateHandler {
    void handleTextMessage(long chatId, String text) throws Exception;

    void handleCallBackMessage(long chatId, String text) throws Exception;
}
