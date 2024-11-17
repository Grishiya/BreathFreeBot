package com.grishiya.BreathFreeBot.botResponse.service;

import com.grishiya.BreathFreeBot.botResponse.entity.model.User;

public interface UserService {
    User findByChatId(Long chatId);

    User saveUser(User user);

    void updateUserState(Long chatId, String state);
}
