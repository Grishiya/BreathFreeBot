package com.grishiya.BreathFreeBot.botResponse.repository;

import com.grishiya.BreathFreeBot.botResponse.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
