package com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl.Begin;

import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.StateHandler;
import com.grishiya.BreathFreeBot.botResponse.model.Page;
import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import com.grishiya.BreathFreeBot.botResponse.model.enums.UserState;
import com.grishiya.BreathFreeBot.botResponse.services.MessageService;
import com.grishiya.BreathFreeBot.botResponse.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
public class BeginStateHandler implements StateHandler {
    private final MessageService messageService;
    private final UserService userService;

    private List<Page> beginPages = Arrays.asList(
            new Page("Cute_Playful_Robot.jpeg", TextForBegin.FIRST_MESSAGE)
    );

    @Override

    public void handle(Update update, UserEntity user) throws IOException {
        if (update.hasCallbackQuery()) {
            handleUpdate(update, user);
        }
    }

    private void handleUpdate(Update update, UserEntity user) throws IOException {
        var callback = update.getCallbackQuery();
        var data = callback.getData();
        if (data.equals("YEAH")) {
            user.setState(UserState.USER_SMOKING_INFO);
            userService.updateUser(user);
            sendBeginMessage(user);
        }
    }

    private void sendBeginMessage(UserEntity user) throws IOException {
        Page page = beginPages.get(0);
        InlineKeyboardMarkup markup = buildKeyboardForStartMessage();
        messageService.createPhotoMessageWithCaptionAndButton(user.getChatId(), page, markup);

    }

    private InlineKeyboardMarkup buildKeyboardForStartMessage() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton forward = new InlineKeyboardButton("Продолжим");
        forward.setCallbackData("CONTINUE");
        rows.add(Collections.singletonList(forward));
//        } else if (pageIndex == totalPages - 1) {
//            InlineKeyboardButton back = new InlineKeyboardButton("⬅ Назад");
//            back.setCallbackData("PAGE:" + (pageIndex - 1));
//            InlineKeyboardButton nameButton = new InlineKeyboardButton("Познакомиться");
//            nameButton.setCallbackData("ENTER_NAME");
//            rows.add(Arrays.asList(back, nameButton));
//        } else if (pageIndex == 1) {
//            InlineKeyboardButton back = new InlineKeyboardButton("⬅ Назад");
//            back.setCallbackData("PAGE:" + (pageIndex - 1));
//            InlineKeyboardButton readyButton = new InlineKeyboardButton("Да, я готов \uD83D\uDCAA");
//            readyButton.setCallbackData("PAGE:" + (pageIndex + 1));
//            rows.add(Arrays.asList(back, readyButton));
//        } else {
//            InlineKeyboardButton back = new InlineKeyboardButton("⬅ Назад");
//            back.setCallbackData("PAGE:" + (pageIndex - 1));
//            InlineKeyboardButton forward = new InlineKeyboardButton("Я доверяю вам \uD83E\uDEF0");
//            forward.setCallbackData("PAGE:" + (pageIndex + 1));
//            rows.add(Arrays.asList(back, forward));


        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
