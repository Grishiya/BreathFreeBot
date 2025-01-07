package com.grishiya.BreathFreeBot.botResponse.repository;

import com.grishiya.BreathFreeBot.botResponse.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
