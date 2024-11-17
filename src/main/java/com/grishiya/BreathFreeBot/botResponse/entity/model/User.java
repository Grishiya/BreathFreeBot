package com.grishiya.BreathFreeBot.botResponse.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "nicotino_users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    @Id
    private Long chatId;
    private String name;
    private LocalDateTime registrationTime;
    private String state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(chatId, user.chatId) && Objects.equals(name, user.name) && Objects.equals(registrationTime, user.registrationTime) && Objects.equals(state, user.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, name, registrationTime, state);
    }
}
