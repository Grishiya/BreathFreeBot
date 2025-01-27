package com.grishiya.BreathFreeBot.botResponse.services.start;

import com.grishiya.BreathFreeBot.botResponse.keyboards.KeyboardForStartMessage;
import com.grishiya.BreathFreeBot.botResponse.model.Page;
import com.grishiya.BreathFreeBot.botResponse.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import javax.print.attribute.standard.PagesPerMinute;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class WelcomeScenarioService {
    private final WelcomePageService welcomePageService;
    private final MessageService messageService;

    private final Map<Long, Integer> currentPageMap = new HashMap<>();
    private final Set<Long> waitingNameSet = new HashSet<>();

    public void sendPage(Long chatId, int pageIndex) throws IOException {
        Page page = welcomePageService.getPage(pageIndex);
        InlineKeyboardMarkup keyboardMarkup = KeyboardForStartMessage.buildKeyboardForStartMessage(pageIndex,
                welcomePageService.getTotalPages());
        messageService.createPhotoMessageWithCaptionAndButton(chatId, page, keyboardMarkup);
    }

    public void handlePageCallback(Long chatId,int messageId, int pageIndex) throws IOException {
        Page page = welcomePageService.getPage(pageIndex);

        var keyboard = KeyboardForStartMessage.buildKeyboardForStartMessage(
                pageIndex,
                welcomePageService.getTotalPages()
        );

        messageService.editPhotoMessageWithCaptionAndKeyboard(chatId, messageId, page, keyboard);
    }
}
