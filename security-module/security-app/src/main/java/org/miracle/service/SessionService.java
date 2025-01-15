package org.miracle.service;


import org.miracle.dto.SessionDTO;
import org.miracle.model.Session;

/**
 * Интерфейс для получения и создания сессии пользователя
 * @author Mark Valiev
 * @since 03.08.2024
 */
public interface SessionService {

    /**
     * Метод, реализация которого создает новую сессию пользователя
     * @param userLogin - логин пользователя
     * @return SessionId - возвращает ид сессии
     */
    String createSession(String userLogin);

    /**
     * Метод, реализация которого позволяет получить существующую сессию
     * @param sessionId - id сессии
     * @return sessionDTO - Обьект передачи данных, представляющий сессию пользователя
     */
    SessionDTO getCurrentSession(String sessionId);

}