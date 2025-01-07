package com.grishiya.BreathFreeBot.botResponse.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandHandler {
//    private final MessageService messageService;
//    private final BrosatorTgBot bot;
//
//    public void handleStartCommand(Long chatId)  {
//        String caption = "Кхе-кхе-кхе... \"Привет... Мы твои лёгкие." +
//                " Ты нас, наверное, редко замечаешь, но мы всегда рядом." +
//                " Правда, последнее время мы совсем ослабли." +
//                " Каждый вдох даётся с трудом. Мы устали бороться с дымом...\"";
//        String buttonText = "Ввести имя";
//        String callBackData = "ENTER_NAME";
//        String fileName= "startPhoto.jpeg";
//
//        try {
//            bot.execute(messageService.createPhotoMessageWithCaptionAndButton(
//                    chatId,
//                    "startPhoto.jpeg",
//                    caption,
//                    buttonText,
//                    callBackData
//            ));
//        } catch (IOException | TelegramApiException e) {
//            log.error("Ошибка при отправке фото", e);
//            sendTextMessage(chatId,"Произошла ошибка при отправке сообщения");
//            }
//        }
//
//    public void sendUnknownCommandMessage(Long chatId) {
//        try {
//            bot.execute(new SendMessage(chatId.toString(), "Я не понимаю эту команду."));
//        } catch (TelegramApiException e) {
//            log.error("Ошибка при отправке неизвестной команды", e);
//        }
//    }
//
//    private void sendTextMessage(Long chatId, String text) {
//        try {
//            bot.execute(new SendMessage(chatId.toString(), text));
//        } catch (TelegramApiException e) {
//            log.error("Ошибка при отправке текстового сообщения", e);
//        }
//    }
}
