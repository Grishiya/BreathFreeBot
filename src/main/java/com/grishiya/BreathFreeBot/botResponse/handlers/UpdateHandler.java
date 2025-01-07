//package com.grishiya.BreathFreeBot.botResponse.handlers;
//
//import com.grishiya.BreathFreeBot.botResponse.controller.BrosatorTgBot;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//@Servicet
//@RequiredArgsConstructor
//@Slf4j
//public class UpdateHandler {
//    @Autowired
//    private MessageHandler messageHandler;
//    @Autowired
//    private CallbackHandler callbackHandler;
//
//    public void handleUpdate(Update update) {
//        try {
//            long chatId = extractChatId(update);
//            if (update.hasMessage() && update.getMessage().hasText()) {
//                messageHandler.handleMessage(update.getMessage());
//            } else if (update.hasCallbackQuery()) {
//                callbackHandler.handleCallback(update.getCallbackQuery());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private long extractChatId(Update update) {
//        if (update.hasMessage()) {
//            return update.getMessage().getChatId();
//        } else if (update.hasCallbackQuery()) {
//            return update.getCallbackQuery().getMessage().getChatId();
//        }
//        return 0;
//    }
//
//}
