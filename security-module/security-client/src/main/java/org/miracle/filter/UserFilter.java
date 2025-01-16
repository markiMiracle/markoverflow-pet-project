package org.miracle.filter;


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
 * Класс фильтрации, проверяющий наличие заголовков, необходимых для работы сервиса
 *
 * @author Mark Valiev
 * @since 16.08.2024
 */
@Order(2)
public class UserFilter implements Filter {

    private static final String deviceHeader = "device";
    private static final String ipHeader = "ip";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        var ip = req.getHeader(ipHeader);
        var device = req.getHeader(deviceHeader);
        if (ip == null || device == null || ip.isEmpty() || device.isEmpty()) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }

}