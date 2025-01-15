package org.miracle.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;

/**
 * Класс, представляющий модель сессии пользователя
 * @author Mark Valiev
 * @since 02.08.2024
 */
@RedisHash("session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Session {

    private String id;

    private String login;

    @Builder.Default
    private LocalDate expiresAt = LocalDate.now().plusDays(7);

}