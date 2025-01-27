package com.grishiya.BreathFreeBot.botResponse.services;

import com.grishiya.BreathFreeBot.botResponse.model.Page;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaBotMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.IOException;

public interface MessageService {


  void createPhotoMessageWithCaptionAndButton(Long chatId, Page page, InlineKeyboardMarkup callbackData) throws IOException;

  void editPhotoMessageWithCaptionAndKeyboard(Long chatId,
                                              int messageId,
                                              Page page,
                                              InlineKeyboardMarkup keyboardMarkup);

  void sendTextMessage(Long chatId, String text);

  void sendMessageWithKeyboard(Long chatId, String text, InlineKeyboardMarkup keyboardMarkup);
}
