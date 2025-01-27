package com.grishiya.BreathFreeBot.botResponse.services;

import com.grishiya.BreathFreeBot.botResponse.model.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final DefaultAbsSender bot;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String botName;

    public MessageServiceImpl() {
        // Инициализируем бот-сендер с необходимым токеном
        bot = new DefaultAbsSender(new DefaultBotOptions()) {
            @Override
            public String getBotToken() {
                return token;
            }
        };
    }

    @Override
    public void createPhotoMessageWithCaptionAndButton(Long chatId, Page page, InlineKeyboardMarkup callbackData)  {
        try {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId.toString());
            ClassPathResource resource = new ClassPathResource("images/" + page.getPhotoName());
            InputFile inputFile = new InputFile(resource.getInputStream(), page.getPhotoName());

            sendPhoto.setPhoto(inputFile);
            sendPhoto.setCaption(page.getText());

            sendPhoto.setReplyMarkup(callbackData);
            bot.execute(sendPhoto);
        } catch (TelegramApiException|IOException e) {
            log.error("Ошибка", e);
        }
    }
@Override
    public void editPhotoMessageWithCaptionAndKeyboard(Long chatId,
                                                       int messageId,
                                                       Page page,
                                                       InlineKeyboardMarkup keyboardMarkup) {
        try {
            EditMessageMedia editMessageMedia = new EditMessageMedia();
            editMessageMedia.setChatId(chatId);
            editMessageMedia.setMessageId(messageId);
            InputMediaPhoto mediaPhoto = new InputMediaPhoto();

            ClassPathResource resource = new ClassPathResource("images/" + page.getPhotoName());
            InputStream is = resource.getInputStream();
            mediaPhoto.setMedia(is, page.getPhotoName());
            mediaPhoto.setCaption(page.getText());

            editMessageMedia.setMedia(mediaPhoto);
            editMessageMedia.setReplyMarkup(keyboardMarkup);
            bot.execute(editMessageMedia);
        } catch (TelegramApiException| IOException e) {
            log.error("Ошибка братец",e);
        }
    }

    @Override
    public void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageWithKeyboard(Long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        message.setReplyMarkup(keyboardMarkup);
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
