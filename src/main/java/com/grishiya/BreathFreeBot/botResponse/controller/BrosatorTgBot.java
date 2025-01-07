//package com.grishiya.BreathFreeBot.botResponse.controller;
//
//import com.grishiya.BreathFreeBot.botResponse.handlers.UpdateHandler;
//import com.grishiya.BreathFreeBot.botResponse.keyboards.KeyboardForStartMessage;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
//import org.telegram.telegrambots.meta.api.objects.InputFile;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.Serializable;
//import java.util.*;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class BrosatorTgBot extends TelegramLongPollingBot {
//    @Value("${bot.name}")
//    private String botName;
//    @Value("${bot.token}")
//    private String botToken;
//    private final UpdateHandler updateHandler;
//
//    private Map<Long, Integer> userPage = new HashMap<>();
//
//    private List<Page> pages = Arrays.asList(
//            new Page("legkiyStart1.jpeg", "Кхе-кхе-кхе… Привет, мы твои лёгкие." +
//                    " Ты нас, наверное, редко " +
//                    "замечаешь, но мы всегда рядом." +
//                    " Правда, последнее время мы совсем ослабли." +
//                    " Каждый вдох даётся с трудом. Мы устали бороться с дымом..."),
//            new Page("legkieStart2.jpeg", "Но, знаешь, мы не сдаёмся!" +
//                    " Если ты хочешь помочь нам — мы готовы начать всё с чистого листа." +
//                    " Мы будем очищаться и бороться вместе, шаг за шагом."),
//            new Page("legkieStart3.jpeg", "Нам очень тяжело , мы задыхаемся , и тонем в смолах"),
//            new Page("legkieStart4.jpeg", "Мы знаем что нужно делать, чтобы спастись." +
//                    " Но, без тебя, нам не справиться - мы бессильны. Как мы можем к тебе обращаться ?")
//    );
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        try {
//            updateHandler.handleUpdate(update);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        List<Object> responses = updateHandler.handleUpdate(update);
////        for (Object r : responses) {
////            try {
////                if (r instanceof SendMessage) {
////                    execute((SendMessage) r);
////                } else if (r instanceof SendPhoto) {
////                    execute((SendPhoto) r);
////                }else {
////                    log.warn("Неизвестный тип сообщения: {}", r.getClass().getName());
////                }
////            } catch (TelegramApiException e) {
////                log.error("Ошибка", e);
////            }
////        }
//    }
//
//
//    private void handleMessage(Message message) throws TelegramApiException {
//        Long chatId = message.getChatId();
//        String text = message.getText();
//        if (text.equals("/start")) {
//            userPage.put(chatId, 0);
//            sendInitialPage(chatId, 0);
//        }
//    }
//
//    private void handleCallbackQuery(Update update) throws Exception {
//        long chatId = update.getCallbackQuery().getMessage().getChatId();
//        int messageId = update.getCallbackQuery().getMessage().getMessageId();
//        String data = update.getCallbackQuery().getData();
//
//        if (data.startsWith("PAGE:")) {
//            handlePageNavigation(chatId, messageId, data);
//        }
//    }
//    private void handlePageNavigation(long chatId, int messageId, String data) throws Exception {
//        int pageIndex = Integer.parseInt(data.split(":")[1]);
//        userPage.put(chatId, pageIndex);
//        editPage(chatId, messageId, pageIndex);
//    }
//
//    private void sendInitialPage(Long chatId, int pageIndex) throws TelegramApiException {
//        Page page = pages.get(pageIndex);
//        SendPhoto sendPhoto = new SendPhoto();
//        sendPhoto.setChatId(chatId.toString());
//        InputStream photoStream = getClass().getResourceAsStream("/images/" + page.getPhotoName());
//        if (photoStream == null) {
//            throw new IllegalArgumentException("Картинка не найдена: " + page.getPhotoName());
//        }
//        sendPhoto.setPhoto(new InputFile(photoStream, page.getPhotoName()));
//        sendPhoto.setCaption(page.getText());
//        sendPhoto.setReplyMarkup(KeyboardForStartMessage.buildKeyboardForStartMessage(pageIndex, pages.size()));
//
//        execute(sendPhoto);
//    }
//
//    private void editPage(Long chatId, int messageId, int pageIndex) throws TelegramApiException {
//        Page page = pages.get(pageIndex);
//        EditMessageMedia editMessageMedia = new EditMessageMedia();
//        editMessageMedia.setChatId(chatId);
//        editMessageMedia.setMessageId(messageId);
//
//        InputStream inputStream = getClass().getResourceAsStream("/images/" + page.getPhotoName());
//        if (inputStream == null) {
//            throw new IllegalArgumentException("Картинка не найдена: " + page.getPhotoName());
//        }
//
//        InputMediaPhoto mediaPhoto = new InputMediaPhoto();
//        mediaPhoto.setMedia(inputStream, page.getPhotoName());
//        mediaPhoto.setCaption(page.getText());
//
//        editMessageMedia.setMedia(mediaPhoto);
//        execute(editMessageMedia);
//
//        EditMessageReplyMarkup replyMarkup = new EditMessageReplyMarkup();
//        replyMarkup.setChatId(chatId);
//        replyMarkup.setMessageId(messageId);
//        replyMarkup.setReplyMarkup(KeyboardForStartMessage.buildKeyboardForStartMessage(pageIndex, pages.size()));
//
//        execute(replyMarkup);
//    }
//
//
//
//
//
//
//
//    private void sendPhotoMessage(Long chatId, String filePath, String caption) throws TelegramApiException {
//        SendPhoto photo = new SendPhoto();
//        photo.setChatId(chatId);
//        photo.setCaption(caption);
//
//        // Используем ClassPathResource для получения файла
//        ClassPathResource resource = new ClassPathResource(filePath);
//        File imageFile;
//        try {
//            imageFile = resource.getFile(); // Получаем файл из ресурсов
//            photo.setPhoto(new InputFile(imageFile)); // Устанавливаем файл
//        } catch (IOException e) {
//            log.error("Ошибка при загрузке файла из ресурсов: {}", filePath, e);
//            throw new TelegramApiException("Не удалось загрузить изображение", e);
//        }
//
//        // Отправляем изображение
//        execute(photo);
//    }
//
//    private void sendMessage(Long chatId, String text) {
//        SendMessage sendMessage = new SendMessage(chatId.toString(), text);
//        try {
//            final Message execute = execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
////    public static void sendPhoto(Long chatId, String filePath) {
////        SendPhoto sendPhoto = new SendPhoto();
////        sendPhoto.setChatId(chatId.toString());
////        InputStream inputStream;
////        inputStream.getClass().getResourceAsStream()
////        InputFile inputFile=new InputFile()
////    }
//
//    @Override
//    public String getBotUsername() {
//        return botName;
//    }
//
//    @Override
//    public String getBotToken() {
//        return botToken;
//    }
//    private static class Page{
//        private final String photoName;
//        private final String text;
//
//        public String getPhotoName() {
//            return photoName;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public Page(String photoName, String text) {
//            this.photoName = photoName;
//            this.text = text;
//        }
//    }
//}