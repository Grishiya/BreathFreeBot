package com.grishiya.BreathFreeBot.botResponse.service;

import com.grishiya.BreathFreeBot.botResponse.entity.model.User;
import com.grishiya.BreathFreeBot.botResponse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User findByChatId(Long chatId) {
        return userRepository.findById(chatId).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUserState(Long chatId, String state) {
        User user = findByChatId(chatId);
        if (user != null) {
            user.setState(state);
            saveUser(user);
        } else {
            user = new User();
            user.setChatId(chatId);
            user.setState(state);
            saveUser(user);
        }
    }
}
