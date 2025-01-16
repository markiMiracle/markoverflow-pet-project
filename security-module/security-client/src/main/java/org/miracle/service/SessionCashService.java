package org.miracle.service;

import org.miracle.dto.SessionDTO;

/**
 * Сервис для получения сессии клиента
 *
 * @author Mark Valiev
 * @since 22.08.2024
 */
public interface SessionCashService {

    /**
     * Получение сессии. Сессия получается из куки SESSIONID
     * @return {@link SessionDTO}
     */
    SessionDTO getSession();

    /**
     * Очистка контекста от сохранённой сессии
     */
    void invalidateSession();
}
