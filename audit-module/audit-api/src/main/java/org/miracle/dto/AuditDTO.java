package org.miracle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Обьект передачи данных аудита пользователя
 * @author Mark Valiev
 * @since 11.09.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditDTO {

    private String userLogin;
    private String userAction;
    private String ip;
    private String device;
    private String sessionId;
    private LocalDate createdAt;

}