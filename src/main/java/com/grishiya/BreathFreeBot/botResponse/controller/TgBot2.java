package com.grishiya.BreathFreeBot.botResponse.controller;

import com.grishiya.BreathFreeBot.botResponse.keyboards.KeyboardForStartMessage;
import com.grishiya.BreathFreeBot.botResponse.model.Page;
import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import com.grishiya.BreathFreeBot.botResponse.model.UserState;
import com.grishiya.BreathFreeBot.botResponse.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;


@Data
@Component
@RequiredArgsConstructor
@Slf4j
public class TgBot2 extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    @Autowired
    private UserService userService;

    private Map<Long, Integer> userPage = new HashMap<>();
    private List<Page> pages = Arrays.asList(
            new Page("legkiyStart1.jpeg", "Кхе-кхе-кхе… Привет, мы твои лёгкие." +
                    " Ты нас, наверное, редко " +
                    "замечаешь, но мы всегда рядом." +
                    " Правда, последнее время мы совсем ослабли." +
                    " Каждый вдох даётся нам с трудом. Мы устали бороться с дымом..."),
            new Page("legkieStart3.jpeg", "Нам очень тяжело , мы задыхаемся , и тонем в смолах. " +
                    "Каждая сигарета забирает у нас силы, делает дыхание тяжёлым, а работу невыносимой"),

            new Page("legkieStart2.jpeg", "Но, знаешь, мы не сдаёмся!" +
                    " Если ты хочешь помочь нам — мы готовы начать всё с чистого листа." +
                    " Мы будем очищаться и бороться вместе, шаг за шагом."),
            new Page("legkieStart4.jpeg", "Нам не нужны чудеса. Нам нужен ты. Твоё решение." +
                    " Мы готовы показать тебе путь, который освободит нас." +
                    " Это не сложно и не страшно, ты справишься!" +
                    "Мы знаем что нужно делать, чтобы спастись." +
                    " Но, без тебя, нам не справиться - мы бессильны!")
    );


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            var data = callbackQuery.getData();
            var chatId = callbackQuery.getMessage().getChatId();
            var messageId = callbackQuery.getMessage().getMessageId();
            if (data.startsWith("PAGE:")) {
                int indexPage = Integer.parseInt(data.split(":")[1]);
                userPage.put(chatId, indexPage);
                editPage(chatId, messageId, indexPage);
            }
            else if (data.startsWith("PAGE_BEGIN1:")) {
                int indexPage = Integer.parseInt(data.split(":")[1]);
                userPage.put(chatId, indexPage);
                editPage(chatId, messageId, indexPage);
            }else if (data.equals("ENTER_NAME")) {
                UserEntity user = userService.findOrCreateUser(chatId, UserState.WAITING_NAME);
                user.setState(UserState.WAITING_NAME);
                userService.updateUser(user);
                sendWaitingNameMessage(chatId);
            }
        }
    }

    private void handleTextMessage(Update update) {
        var chatId = update.getMessage().getChatId();
        var message = update.getMessage();
        var text = message.getText();
        if (text.equals("/start")) {
            Optional<UserEntity> optUser = userService.findByChatId(chatId);
            if (optUser.isEmpty()) {
                sendPage(chatId, 0);
            } else {
                UserEntity user = optUser.get();
                if (user.getState() == UserState.WAITING_NAME) {
                    sendWaitingNameMessage(chatId);
                } else if (user.getState() == UserState.BEGIN) {
                  sendFirstBeginMessage(chatId, "Ещё раз привет, "+ user.getName()+"!" +
                          " Очень приятно познакомиться." +
                          " Мы невероятно рады, что ты сделал этот важный шаг." +
                          " Ты решил освободить себя и начать жить свободно, без сигарет!\n" +
                          "Мы с тобой, и вместе у нас всё получится!" );
                } else {
                    sendMessage(chatId, "Что тыт тут делаешь?");
                }
            }
        } else {
            Optional<UserEntity> optUser = userService.findByChatId(chatId);
            if (optUser.isPresent()) {
                UserEntity user = optUser.get();
                // Если ждём имя
                if (user.getState() == UserState.WAITING_NAME) {

                    // Сохраняем
                    user.setName(text);
                    user.setCreatedAt(LocalDateTime.now());
                    user.setState(UserState.BEGIN);
                    userService.updateUser(user);
                }
                if (user.getState() == UserState.BEGIN) {
                 sendFirstBeginMessage(chatId, "Ещё раз привет, "+ user.getName()+"!" +
                         " Очень приятно познакомиться." +
                         " Мы невероятно рады, что ты сделал этот важный шаг." +
                         " Ты решил освободить себя и начать жить свободно, без сигарет!\n" +
                         "Мы с тобой, и вместе у нас всё получится!" );
                    sendMessage(chatId, "Сегодня " + user.getCreatedAt().getDayOfMonth() + " день начала");

                }
            }
        }
    }

    private void onStartCommand(Long chatId) {
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPage(Long chatId, int pageIndex) {
        sendPhotoForUser(chatId, pageIndex);
    }

    private void sendWaitingNameMessage(Long chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());

        InputStream inputStream = getClass().getResourceAsStream("/images/Anthropomorphic_Lungs.jpeg");
        if (inputStream == null) {
            log.info("Не удалось найти файл : " + "Anthropomorphic_Lungs.jpeg");
            return;
        }
        sendPhoto.setPhoto(new InputFile(inputStream, "Anthropomorphic_Lungs.jpeg"));
        sendPhoto.setCaption("Пожалуйста, напиши нам, как тебя зовут. Нам важно знать твоё имя," +
                " чтобы мы могли обращаться к тебе лично и звать тебя так, как тебе приятно \uD83E\uDD70");
        try {
            execute(sendPhoto);
        } catch (
                TelegramApiException e
        ) {
            e.printStackTrace();
        }
    }

    private void sendPhotoForUser(Long chatId, int pageIndex) {
        Page page = pages.get(pageIndex);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());

        InputStream inputStream = getClass().getResourceAsStream("/images/" + page.getPhotoName());
        if (inputStream == null) {
            log.info("Не удалось найти файл : " + page.getPhotoName());
            return;
        }
        sendPhoto.setPhoto(new InputFile(inputStream, page.getPhotoName()));
        sendPhoto.setCaption(page.getText());
        sendPhoto.setReplyMarkup(KeyboardForStartMessage.buildKeyboardForStartMessage(pageIndex, pages.size()));
        try {
            execute(sendPhoto);
        } catch (
                TelegramApiException e
        ) {
            e.printStackTrace();
        }
    }
    private void sendFirstBeginMessage(Long chatId,String caption) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());

        InputStream inputStream = getClass().getResourceAsStream("/images/Anthropomorphic_Lungs_Welcoming.jpeg");
        if (inputStream == null) {
            log.info("Не удалось найти файл : " + "Anthropomorphic_Lungs_Welcoming.jpeg");
            return;
        }
        sendPhoto.setPhoto(new InputFile(inputStream, "Anthropomorphic_Lungs.jpeg"));
        sendPhoto.setCaption(caption);
        sendPhoto.setReplyMarkup(KeyboardForStartMessage.buildKeyboardForFirstBeginMessage(0,1));
        try {
            execute(sendPhoto);
        } catch (
                TelegramApiException e
        ) {
            e.printStackTrace();
        }
    }

    private void editPage(Long chatId, Integer messageId, int pageIndex) {
        Page page = pages.get(pageIndex);
        EditMessageMedia editMedia = new EditMessageMedia();
        editMedia.setChatId(chatId);
        editMedia.setMessageId(messageId);
        InputMediaPhoto newPhoto = new InputMediaPhoto();

        InputStream is = getClass().getResourceAsStream("/images/" + page.getPhotoName());
        if (is == null) {
            log.info("Не удалось найти файл : " + page.getPhotoName());
            return;
        }
        newPhoto.setMedia(is, page.getPhotoName());
        newPhoto.setCaption(page.getText());
        editMedia.setMedia(newPhoto);
        try {
            execute(editMedia);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
        editMarkup.setChatId(chatId);
        editMarkup.setMessageId(messageId);
        editMarkup.setReplyMarkup(KeyboardForStartMessage.buildKeyboardForStartMessage(pageIndex, pages.size()));
        try {
            execute(editMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


}
