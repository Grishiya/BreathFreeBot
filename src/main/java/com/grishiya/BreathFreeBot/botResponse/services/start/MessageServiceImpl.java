package com.grishiya.BreathFreeBot.botResponse.services.start;

import com.grishiya.BreathFreeBot.botResponse.services.start.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Override
    public SendPhoto createPhotoMessageWithCaptionAndButton(Long chatId, String fileName, String caption, String buttonText, String callbackData) throws IOException {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        ClassPathResource resource = new ClassPathResource("images/" + fileName);
        InputFile inputFile = new InputFile(resource.getInputStream(), fileName);
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setCaption(caption);

        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callbackData)
                .build();

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(Collections.singletonList(Collections.singletonList(button)));

        sendPhoto.setReplyMarkup(markup);

        return sendPhoto;
    }

    //    @Override
//    public SendMessage sendTextMessage(Long chatId, String text) {
//
//        return new SendMessage(chatId.toString(), text);
//    }
//
//    @Override
//    public SendMessage createDefaultMessage(Long chatId) {
//        return new SendMessage(chatId.toString(), "Что?");
//    }
//
//    @Override
//    public SendMessage createMessageWithButton(Long chatId, String text,
//                                               String buttonText, String callBackData) {
//        SendMessage message = new SendMessage(chatId.toString(), text);
//        InlineKeyboardButton button = InlineKeyboardButton.builder()
//                .text(buttonText)
//                .callbackData(callBackData)
//                .build();
//        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//        markup.setKeyboard(Collections.singletonList(Collections.singletonList(button)));
//        message.setReplyMarkup(markup);
//        return message;
//    }
//
//    @Override
//    public SendPhoto createPhotoMessageFromResources(Long chatId, String fileName, String caption) {
//        SendPhoto photo = new SendPhoto();
//        photo.setChatId(chatId.toString());
//        // Здесь вы устанавливаете файл через InputStream или File
//        // photo.setPhoto(new InputFile(resource.getInputStream(), fileName));
//        if (caption != null && !caption.isEmpty()) {
//            photo.setCaption(caption);
//        }
//        return photo; // Возвращаем SendPhoto
//    }
}
