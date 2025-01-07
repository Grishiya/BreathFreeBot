package com.grishiya.BreathFreeBot.botResponse.services;

import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import com.grishiya.BreathFreeBot.botResponse.model.UserState;
import com.grishiya.BreathFreeBot.botResponse.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Map<Long, Integer> userPageIndex = new HashMap<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findOrCreateUser(Long chatId,  UserState state) {
        return userRepository.findById(chatId).orElseGet(() -> {
            UserEntity user = new UserEntity(chatId, null,null, state);
            return userRepository.save(user);
        });
    }

    public Optional<UserEntity> findByChatId(Long chatId) {
        return userRepository.findById(chatId);
    }

    public void updateUserState(Long chatId, UserState state) {
        UserEntity user = userRepository.findById(chatId).orElseThrow();
        user.setState(state);
        userRepository.save(user);
    }

    public UserState getUserState(long chatId) {
        UserEntity user = userRepository.findById(chatId).orElseThrow();
        return user.getState();
    }

    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }


}
