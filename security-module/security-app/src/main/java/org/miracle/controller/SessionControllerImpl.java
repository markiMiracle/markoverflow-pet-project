package org.miracle.controller;

import org.miracle.dto.SessionDTO;
import org.miracle.service.SessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * Класс, являющийся обработчиком маршрутов для сущности сессии
 * @author Mark Valiev
 * @since 09.08.2024
 */
@AllArgsConstructor
@RestController
public class SessionControllerImpl implements SessionController {

    private SessionService sessionService;

    @GetMapping("/session/{sessionId}")
    public SessionDTO getSession(@PathVariable("sessionId") String sessionId) {
        return sessionService.getCurrentSession(sessionId);
    }

}
