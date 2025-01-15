package org.miracle.service;

import org.miracle.dto.UserDTO;

/**
 * Интерфейс для создания сервиса сущности пользователя
 * @author Mark Valiev
 * @since 03.08.2024
 */
public interface UserService {

    /**
     * Метод, регистрирующий нового пользователя
     * @param dto - Обьект передачи данных нового пользователя
     * @return SessionId - сессионная кука для работы сервиса
     */
    String createUser(UserDTO dto);

    /**
     * Метод для аунтефикации пользователя
     * @param dto - Обьект передачи данных пользователя
     * @return SessionId - сессионная куку для работы сервиса
     */
    String login(UserDTO dto);

}