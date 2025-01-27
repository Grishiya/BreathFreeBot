package com.grishiya.BreathFreeBot.botResponse.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class KeyboardForStartMessage {
    public static InlineKeyboardMarkup buildKeyboardForStartMessage(int pageIndex, int totalPages) {
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
            rows.add(Arrays.asList(back,readyButton));
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

    public static InlineKeyboardMarkup buildKeyboardForFirstBeginMessage(int pageIndex, int totalPages) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        if (pageIndex == 0) {
            InlineKeyboardButton forward = new InlineKeyboardButton("Продолжить \u27A1\uFE0F");
            forward.setCallbackData("PAGE_BEGIN1:" + (pageIndex + 1));
            rows.add(Collections.singletonList(forward));
        } else if (pageIndex == totalPages - 1) {
            InlineKeyboardButton back = new InlineKeyboardButton("\u2B05\uFE0F");
            back.setCallbackData("PAGE_BEGIN1:" + (pageIndex - 1));
            InlineKeyboardButton nameButton = new InlineKeyboardButton("Продолжить \u27A1\uFE0F");
            nameButton.setCallbackData("NEXT_BEGIN_MESSAGE");
            rows.add(Arrays.asList(back, nameButton));
        }else {

            InlineKeyboardButton back = new InlineKeyboardButton("⬅");
            back.setCallbackData("PAGE:" + (pageIndex - 1));
            InlineKeyboardButton forward = new InlineKeyboardButton("➡");
            forward.setCallbackData("PAGE:" + (pageIndex + 1));
            rows.add(Arrays.asList(back, forward));
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

}
