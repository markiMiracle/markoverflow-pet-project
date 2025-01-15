package org.miracle.service;

import lombok.AllArgsConstructor;
import org.miracle.dto.SessionDTO;
import org.miracle.exception.ResourceNotFoundException;
import org.miracle.mapper.SessionMapper;
import org.miracle.model.Session;
import org.miracle.repository.SessionRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.UUID;


/**
 * Класс, реализующйи функционал интерфейса SessionService
 * @author Mark Valiev
 * @since 03.08.2024
 */
@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    public final static String SESSION_COOKIE_NAME = "SESSIONID";

    private SessionRepository repository;

    private SessionMapper mapper;

    private HttpServletResponse response;

    @Override
    public String createSession(String userLogin) {
        String sessionId = UUID.randomUUID().toString();
        Session session = Session.builder().login(userLogin).id(sessionId).build();
        repository.save(session);
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        cookie.setPath("/");
        response.addCookie(cookie);
        return cookie.getValue();
    }

    @Override
    public SessionDTO getCurrentSession(String sessionId) {
        Session session = repository.findById(sessionId).orElseThrow(() -> new ResourceNotFoundException("Сессия не валидна"));
        if (session.getExpiresAt().isAfter(LocalDate.now())) {
            return mapper.map(session);
        }
        repository.deleteById(sessionId);
        throw new ResourceNotFoundException("Сессия не валидна");
    }

}