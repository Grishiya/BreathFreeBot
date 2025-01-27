package com.grishiya.BreathFreeBot.botResponse.model;

import com.grishiya.BreathFreeBot.botResponse.model.enums.SmokingDuration;
import com.grishiya.BreathFreeBot.botResponse.model.enums.SmokingType;
import jakarta.persistence.*;

@Entity
public class SmokingInfo {
    @Id
    private Long chatId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "chat_id")
    private UserEntity user;
    @Enumerated(EnumType.STRING)
    private SmokingDuration duration;
    @Enumerated(EnumType.STRING)
    private SmokingType smokingType;
}

