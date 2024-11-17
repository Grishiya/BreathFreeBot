package com.grishiya.BreathFreeBot.botResponse.responses;

import org.springframework.stereotype.Component;

@Component
public class BotResponses {
    public String getWelcomeMessage() {
        return "Добро пожаловать в бот 'Свободный Вдох'!" +
                " Я помогу вам на пути к здоровой и свободной от никотина жизни.";
    }

    public  String getStartQuitMessage() {
        return "Ты сделал важный шаг, чтобы бросить курить! Я буду рядом, помогая и поддерживая тебя.";
    }
}
