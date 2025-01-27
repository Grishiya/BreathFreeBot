package com.grishiya.BreathFreeBot.botResponse.services;

import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import com.grishiya.BreathFreeBot.botResponse.model.enums.UserState;
import com.grishiya.BreathFreeBot.botResponse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Set<Long> waitingNameUsers = new HashSet<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void findOrCreateUser(Long chatId, String name) {
        if (!waitingNameUsers.contains(chatId)) {
            return;
        } else {
            waitingNameUsers.remove(chatId);
            UserEntity user = new UserEntity();
            user.setChatId(chatId);
            user.setName(name);
            user.setCreatedAt(LocalDateTime.now());
            user.setState(UserState.BEGIN);
            userRepository.save(user);
        }
    }

    public UserEntity createUser(Long chatId) {
        UserEntity user = new UserEntity();
        user.setChatId(chatId);
        user.setCurrentPage(0);
        userRepository.save(user);
        return user; // <-- возвращаем
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

    private boolean isWaitingForName(Long chatId) {
        return waitingNameUsers.contains(chatId);
    }

    public void setWaitingForName(Long chatId) {
        waitingNameUsers.add(chatId);
    }


}
