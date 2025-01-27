package com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl;

import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.StateHandler;
import com.grishiya.BreathFreeBot.botResponse.keyboards.KeyboardForStartMessage;
import com.grishiya.BreathFreeBot.botResponse.model.Page;
import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import com.grishiya.BreathFreeBot.botResponse.model.enums.UserState;
import com.grishiya.BreathFreeBot.botResponse.services.MessageService;
import com.grishiya.BreathFreeBot.botResponse.services.UserService;
import com.grishiya.BreathFreeBot.botResponse.services.start.WelcomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewStateHandlers implements StateHandler {
    private final MessageService messageService;
    private final WelcomePageService pageService;
    private final UserService userService;

    @Override
    public void handle(Update update, UserEntity user) throws IOException {
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery(), user);
        } else {
            messageService.sendTextMessage(user.getChatId(), "Используй кнопки для навигации");
        }
    }

    private void handleCallback(CallbackQuery callbackQuery, UserEntity user) throws IOException {
        String data = callbackQuery.getData();
        var messageId = callbackQuery.getMessage().getMessageId();
        long chatId= user.getChatId();

        if (data.startsWith("PAGE:")) {
            int pageIndex = Integer.parseInt(data.split(":")[1]);
            showPage(user.getChatId(), messageId, pageIndex);
        } else if (data.equals("ENTER_NAME")) {
            userService.updateUserState(user.getChatId(), UserState.WAITING_NAME);
            inputNameMessage(chatId);
        }
    }

    public void startWelcomeFlow(UserEntity user) throws IOException {
        Page page = pageService.getPage(0);
        InlineKeyboardMarkup keyboardMarkup = KeyboardForStartMessage.buildKeyboardForStartMessage(0,
                pageService.getTotalPages());
        messageService.createPhotoMessageWithCaptionAndButton(user.getChatId(), page, keyboardMarkup);
    }

    private void showPage(Long chatId, int messageId, int pageIndex) {
        Page page = pageService.getPage(pageIndex);
        InlineKeyboardMarkup keyboardMarkup = buildKeyboardForStartMessage(pageIndex, pageService.getTotalPages());
        messageService.editPhotoMessageWithCaptionAndKeyboard(chatId, messageId, page, buildKeyboardForStartMessage(pageIndex, pageService.getTotalPages()));
    }

    private void inputNameMessage(Long chatId) throws IOException {
        Page page = new Page("Anthropomorphic_Lungs.jpeg", "Пожалуйста,напиши как тебя зовут");
        messageService.createPhotoMessageWithCaptionAndButton(chatId, page, null);
    }
    private InlineKeyboardMarkup buildKeyboardForStartMessage(int pageIndex, int totalPages) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        if (pageIndex == 0) {
            InlineKeyboardButton forward = new InlineKeyboardButton("Что случилось?");
            forward.setCallbackData("PAGE:" + (pageIndex + 1));
            rows.add(Collections.singletonList(forward));
        } else if (pageIndex == totalPages - 1) {
            InlineKeyboardButton back = new InlineKeyboardButton("⬅ Назад");
            back.setCallbackData("PAGE:" + (pageIndex - 1));
            InlineKeyboardButton nameButton = new InlineKeyboardButton("Познакомиться");
            nameButton.setCallbackData("ENTER_NAME");
            rows.add(Arrays.asList(back, nameButton));
        } else if (pageIndex == 1) {
            InlineKeyboardButton back = new InlineKeyboardButton("⬅ Назад");
            back.setCallbackData("PAGE:" + (pageIndex - 1));
            InlineKeyboardButton readyButton = new InlineKeyboardButton("Да, я готов \uD83D\uDCAA");
            readyButton.setCallbackData("PAGE:" + (pageIndex + 1));
            rows.add(Arrays.asList(back, readyButton));
        } else {
            InlineKeyboardButton back = new InlineKeyboardButton("⬅ Назад");
            back.setCallbackData("PAGE:" + (pageIndex - 1));
            InlineKeyboardButton forward = new InlineKeyboardButton("Я доверяю вам \uD83E\uDEF0");
            forward.setCallbackData("PAGE:" + (pageIndex + 1));
            rows.add(Arrays.asList(back, forward));

        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
