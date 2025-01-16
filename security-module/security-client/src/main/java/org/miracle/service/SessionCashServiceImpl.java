package org.miracle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miracle.client.SessionClient;
import org.miracle.dto.SessionDTO;
import org.miracle.exception.InvalidSessionException;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реализация {@link SessionCashService}
 *
 * @author Mark Valiev
 * @since 22.08.2024
 */
@Slf4j
@RequiredArgsConstructor
public class SessionCashServiceImpl implements SessionCashService {

    private static final String SESSION_COOKIE = "SESSIONID";

    private final SessionClient httpClient;
    private final HttpServletRequest httpServletRequest;
    private final ConcurrentHashMap<String, SessionDTO> sessions = new ConcurrentHashMap<>();

    @Override
    public SessionDTO getSession() {
        var sessionId = getSessionCookie();
        log.debug("Попытка получения сессии {}", sessionId);
        return sessions.computeIfAbsent(sessionId, key -> {
            try {
                return httpClient.getSessionById(key);
            } catch (Exception e) {
                log.error("Произошла ошибка при получении сессии {}", sessionId);
                throw new InvalidSessionException(sessionId);
            }
        });
    }

    @Override
    public void invalidateSession() {
        var sessionId = getSessionCookie();
        log.debug("Удаление сессии {} из контекста", sessionId);
        sessions.remove(sessionId);
    }

    private String getSessionCookie() {
        log.debug("Попытка получения сессии");
        return WebUtils.getCookie(httpServletRequest, SESSION_COOKIE).getValue();
    }
}
