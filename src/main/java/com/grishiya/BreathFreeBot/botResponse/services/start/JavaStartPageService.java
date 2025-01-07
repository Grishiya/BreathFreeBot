//package com.grishiya.BreathFreeBot.botResponse.services.start;
//
//import com.grishiya.BreathFreeBot.botResponse.controller.BrosatorTgBot;
//import com.grishiya.BreathFreeBot.botResponse.keyboards.KeyboardForStartMessage;
//import com.grishiya.BreathFreeBot.botResponse.model.Page;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
//import org.telegram.telegrambots.meta.api.objects.InputFile;
//import org.telegram.telegrambots.meta.bots.AbsSender;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.List;
//
//public class JavaStartPageService {
//    @Autowired
//    private AbsSender absSender;
//    private List<Page> pages = Arrays.asList(
//            new Page("legkiyStart1.jpeg", "Кхе-кхе-кхе… Привет, мы твои лёгкие." +
//                    " Ты нас, наверное, редко " +
//                    "замечаешь, но мы всегда рядом." +
//                    " Правда, последнее время мы совсем ослабли." +
//                    " Каждый вдох даётся с трудом. Мы устали бороться с дымом..."),
//            new Page("legkieStart2.jpeg", "Но, знаешь, мы не сдаёмся!" +
//                    " Если ты хочешь помочь нам — мы готовы начать всё с чистого листа." +
//                    " Мы будем очищаться и бороться вместе, шаг за шагом."),
//            new Page("legkieStart3.jpeg", "Нам очень тяжело , мы задыхаемся , и тонем в смолах"),
//            new Page("legkieStart4.jpeg", "Мы знаем что нужно делать, чтобы спастись." +
//                    " Но, без тебя, нам не справиться - мы бессильны. Как мы можем к тебе обращаться ?")
//    );
//
//    private void sendInitialPage(Long chatId, int pageIndex) throws TelegramApiException {
//     Page page = pages.get(pageIndex);
//        SendPhoto sendPhoto = new SendPhoto();
//        sendPhoto.setChatId(chatId.toString());
//        InputStream photoStream = getClass().getResourceAsStream("/images/" + page.getPhotoName());
//        if (photoStream == null) {
//            throw new IllegalArgumentException("Картинка не найдена: " + page.getPhotoName());
//        }
//        sendPhoto.setPhoto(new InputFile(photoStream, page.getPhotoName()));
//        sendPhoto.setCaption(page.getText());
//        sendPhoto.setReplyMarkup(KeyboardForStartMessage.buildKeyboardForStartMessage(pageIndex, pages.size()));
//
//        absSender.execute(sendPhoto);
//    }
//
//}
