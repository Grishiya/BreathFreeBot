package com.grishiya.BreathFreeBot.botResponse.services.begin;

import com.grishiya.BreathFreeBot.botResponse.model.Page;
import com.grishiya.BreathFreeBot.botResponse.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BeginPageService {
    private final UserService userService;
    private List<Page> beginPage = Arrays.asList(
            new Page("begin1.jpg",
                    "[Имя], поздравляем тебя! 🎉\n\n" +
                            "Ты сделал **осознанный шаг**. Ты сам решил изменить свою жизнь, и это самое важное. \n\n" +
                            "Не потому что тебя заставили. Не потому что ты должен. А потому что **ты этого хочешь**.\n\n" +
                            "🕊 Ты уже ближе к свободе, чем когда-либо прежде.\n\n" +
                            "Миллионы людей по всему миру верили, что курение приносит удовольствие. \n" +
                            "Но потом они поняли правду – это **глупая ловушка**, которая держит нас в страхе.\n\n" +
                            "Теперь ты один из тех, кто **разрушает эту иллюзию**. Ты поймёшь, насколько всё просто. \n" +
                            "Тебе **не нужно бороться** – ты просто осознаешь, что уже свободен! \n\n" +
                            "⏳ Сегодня **[дата, время]** – твоя новая точка отсчёта. Запомни этот момент. Он важнее, чем ты думаешь.\n\n" +
                            "Ты готов увидеть, как изменится твоя жизнь без никотина?"
            ));

    public Page getPage() {
        return beginPage.get(0);
    }
}
