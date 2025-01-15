package org.miracle.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Класс фильтрации, предназначенный для логирования SessionFilter и UserFilter
 * @author Mark Valiev
 * @since 16.08.2024
 */
@Slf4j
@Order(1)
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        log.info("Logging Request  {} : {}, Cookies : {}", req.getMethod(), req.getRequestURI(), req.getCookies());

        chain.doFilter(request, response);

        log.info("Logging Response : {}, Content-Type : {}", res.getStatus(), res.getContentType());
    }

}