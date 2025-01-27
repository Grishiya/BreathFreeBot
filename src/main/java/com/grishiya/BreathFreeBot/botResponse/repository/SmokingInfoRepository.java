package com.grishiya.BreathFreeBot.botResponse.repository;

import com.grishiya.BreathFreeBot.botResponse.model.SmokingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmokingInfoRepository extends JpaRepository<SmokingInfo, Long> {
        Optional<SmokingInfo> findByChatId(Long chatId);
}
