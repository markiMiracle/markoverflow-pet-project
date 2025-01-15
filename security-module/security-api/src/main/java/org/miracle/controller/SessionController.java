package org.miracle.controller;

import org.miracle.dto.SessionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Интерфейс обработчика маршрута сесссии
 * @author Mark Valiev
 * @since 09.08.2024
 */
@RestController
public interface SessionController {

    @GetMapping("/session/${sessionId}")
    SessionDTO getSession(@PathVariable("sessionId") String sessionId);

}
