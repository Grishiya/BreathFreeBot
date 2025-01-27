package com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitingNameHandler implements StateHandler {
    private final UserService userService;
    private final MessageService messageService;

    @Override
    public void handle(Update update, UserEntity user) throws IOException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var userName = update.getMessage().getText();
            var time = LocalDateTime.now();
            user.setName(userName);
            user.setCreatedAt(time);
            userService.updateUser(user);
            sendCongratulationMessage(user);
            user.setState(UserState.BEGIN);
            userService.updateUser(user);

            }
        }

    private void sendMessage(Long chatId, String text) {
        messageService.sendTextMessage(chatId, text);

    }

    private String dateTimeStr(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("dd MM yyyy 'года' 'в' HH:mm"));
    }

    private void sendCongratulationMessage(UserEntity user) throws IOException {
        String message = String.format("""
                🌟 %s! Сегодня %s – особенный день! 🎉  

                📅 Это день, когда ты принял *осознанное решение* изменить свою жизнь.  
                🚭 Ты решил узнать, как избавиться от курения легко и навсегда – без страха! Без боли! Без мучений!   

                🔥 С этого момента ты идёшь к * свободе *.  
                Не важно, курил ты год, десять лет или больше.  
                Не важно, какие сигареты, вейпы или айкос ты использовал.  

                ✨ Теперь есть только одно – *твой выбор, твоя жизнь, твоя свобода.*  
                И мы будем рядом *на всём этом пути!* 💪😊  
                """, user.getName(), dateTimeStr(user.getCreatedAt()));
        Page page = new Page("Anthropomorphic_Lungs_Welcoming.jpeg",
                message);
        InlineKeyboardMarkup markup = yeahButton();
        messageService.createPhotoMessageWithCaptionAndButton(user.getChatId(), page,
                markup);
    }

    private InlineKeyboardMarkup yeahButton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        InlineKeyboardButton yeahButton = new InlineKeyboardButton("Ура \uD83C\uDF89");
        yeahButton.setCallbackData("YEAH");
        row.add(Arrays.asList(yeahButton));
        inlineKeyboardMarkup.setKeyboard(row);
        return inlineKeyboardMarkup;
    }
}



