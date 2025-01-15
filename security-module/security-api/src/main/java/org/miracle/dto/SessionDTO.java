package org.miracle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, являющийся обработчиком маршрутов для сущности сессии
 * @author Mark Valiev
 * @since 09.08.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    private String login;

}
