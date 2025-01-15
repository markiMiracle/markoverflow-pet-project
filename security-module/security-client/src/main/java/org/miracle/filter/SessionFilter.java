package org.miracle.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miracle.exception.InvalidSessionException;
import org.miracle.service.SessionCashService;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс фильтрации, проверяющий валидность сессии пользователя
 * @author Mark Valiev
 * @since 29.08.2024
 */
@Order(3)
@Slf4j
@AllArgsConstructor
public class SessionFilter implements Filter {

    private static final String SESSION_COOKIE = "SESSIONID";

    private SessionCashService sessionService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("Session validation in progress");

        var sessionId = WebUtils.getCookie(request, SESSION_COOKIE);
        if (sessionId == null || sessionId.getValue().isEmpty()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            sessionService.getSession();
            log.info("Validation successful");
            chain.doFilter(request, response);
        } catch (InvalidSessionException | ServletException ex) {
            log.info("Validation failed {}", ex.toString());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        sessionService.invalidateSession();
    }
}