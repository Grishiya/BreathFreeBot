//package com.grishiya.BreathFreeBot.botResponse.handlers;
//
//import com.grishiya.BreathFreeBot.botResponse.handlers.stateHandlers.stateHandlersImpl.NewStateHandlers;
//import com.grishiya.BreathFreeBot.botResponse.model.Page;
//import com.grishiya.BreathFreeBot.botResponse.model.enums.UserState;
//import com.grishiya.BreathFreeBot.botResponse.services.MessageService;
//import com.grishiya.BreathFreeBot.botResponse.services.UserService;
//import com.grishiya.BreathFreeBot.botResponse.services.start.WelcomeScenarioService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class CallbackHandler {
//    private final UserService userService;
//    private final NewStateHandlers welcomeStateHandlers;
//    private final MessageService messageService;
//
//    public void handleCallback(Long chatId,int messageId, String data) throws Exception {
//        var optUser = userService.findByChatId(chatId);
//        var user = optUser.get();
//        if (user.getState().equals(UserState.NEW)) {
//            welcomeStateHandlers.handle();
////            if (data.startsWith("PAGE:")) {
////                int pageIndex = Integer.parseInt(data.split(":")[1]);
////                welcomeScenarioService.handlePageCallback(chatId,messageId, pageIndex);
////            }
////            if (data.equals("ENTER_NAME")) {
////                userService.setWaitingForName(chatId);
////                messageService.createPhotoMessageWithCaptionAndButton(chatId, new Page("Anthropomorphic_Lungs.jpeg", "Пожалуйста,напиши как тебя зовут"), null);
//            }else {
//                System.out.println("?");
//            }
//        }
//
//
//
//    }
//
