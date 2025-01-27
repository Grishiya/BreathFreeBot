package com.grishiya.BreathFreeBot.botResponse.model;

import com.grishiya.BreathFreeBot.botResponse.model.enums.UserState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Сущность пользователя, хранящая всю необходимую информацию
 * для отслеживания процесса отказа от курения.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    /**
     * Уникальный идентификатор пользователя (chatId в Telegram).
     */
    @Id
    private Long chatId;

    /**
     * Имя пользователя (как он представился).
     */
    private String name;

    /**
     * Дата и время, когда пользователь впервые зашёл в бота.
     */
    private LocalDateTime createdAt;

    /**
     * Текущее состояние сценария (NEW, WAITING_NAME, STARTED и т.д.).
     */
    @Enumerated(EnumType.STRING)
    private UserState state = UserState.NEW;

    /**
     * Дата, когда пользователь решил бросить курить.
     */
    private LocalDateTime quitDate;

    /**
     * Количество дней, в течение которых пользователь не курил.
     */
    private int daysWithoutSmoking;
    @OneToOne
    private SmokingInfo smokingInfo;

    /**
     * Количество сигарет, которые пользователь выкуривал до отказа.
     */
    private int cigarettesPerDay;

    /**
     * Использует ли пользователь цитизин (лекарство).
     */
    private boolean usingCitizine;

    /**
     * Номер дня приёма цитизина.
     * Например, если сегодня 3-й день курса.
     */
    private int citizineDay;

    /**
     * Дата и время начала приёма цитизина.
     */
    private LocalDateTime citizineStartDate;

    /**
     * Список, где каждый элемент отражает факт приёма таблетки в конкретный момент.
     * Например, True, если таблетка была принята, False — если пропущена.
     */
    @ElementCollection
    private List<Boolean> citizinePillsTaken = new ArrayList<>();

    /**
     * Список достижений пользователя.
     * Например, «1 неделя без сигарет», «10 дней без срывов» и т.д.
     */
    @ElementCollection
    private List<String> achievements = new ArrayList<>();

    /**
     * Множество страниц или статей, которые пользователь уже просматривал.
     * Используется, чтобы не показывать повторно одну и ту же информацию.
     */
    @ElementCollection
    private Set<String> viewedArticles = new HashSet<>();
    private int currentPage;
    private Integer lastMessageId;
}