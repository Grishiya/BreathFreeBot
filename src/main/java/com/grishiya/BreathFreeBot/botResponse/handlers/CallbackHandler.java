//package com.grishiya.BreathFreeBot.botResponse.handlers;
//
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//public class CallbackHandler {
//    public void handleCallbackQuery(Update update) throws Exception {
//        long chatId = update.getCallbackQuery().getMessage().getChatId();
//        int messageId = update.getCallbackQuery().getMessage().getMessageId();
//        String data = update.getCallbackQuery().getData();
//
//        if (data.startsWith("PAGE:")) {
//            handlePageNavigation(chatId, messageId, data);
//        }
//    }
//}
