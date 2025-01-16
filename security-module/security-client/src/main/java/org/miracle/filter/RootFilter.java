package org.miracle.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miracle.service.JWTService;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс фильтрации, проверяющий рут-сессию для взаимодействия между сервисами
 * @author Mark Valiev
 * @since 15.01.2025
 */
@Order(4)
@Slf4j
@AllArgsConstructor
public class RootFilter implements Filter {

    private final static String TOKEN_ROOT_COOKIE_NAME = "ROOT_TOKEN";

    private JWTService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("Root-token validation in progress");

        var tokenCookie = WebUtils.getCookie(request, TOKEN_ROOT_COOKIE_NAME);
        if (tokenCookie == null || tokenCookie.getValue().isEmpty()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            jwtService.verifyRootToken();
            log.info("Validation successful");
            chain.doFilter(request, response);
        } catch (Exception ex) {
            log.info("Validation failed, {}", ex.toString());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
