package org.miracle.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.miracle.annotation.ForumAudit;
import org.miracle.annotation.SecurityAudit;
import org.miracle.client.AuditClient;
import org.miracle.client.SessionClient;
import org.miracle.dto.AuditDTO;
import org.miracle.service.JWTService;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * Обработчик аннотации {@link SecurityAudit}, создает DTO аудита и отправляет в сервис аудита
 * @author Mark Valiev
 * @since 13.09.2024
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class AuditAspect {

    private static final String IP_HEADER = "ip";
    private static final String DEVICE_HEADER = "device";
    private static final String SESSION_ID_COOKIE_NAME = "SESSIONID";

    private final HttpServletRequest request;
    private final AuditClient auditClient;
    private final SessionClient sessionClient;
    private final JWTService jwtService;

    @AfterReturning(pointcut = "@annotation(audit)", returning = "sessionId")
    public void afterCompleteSecurity(SecurityAudit audit, String sessionId) throws Exception {
        AuditDTO auditDTO = AuditDTO.builder()
                .userLogin(sessionClient.getSessionById(sessionId).getLogin())
                .sessionId(sessionId)
                .ip(request.getHeader(IP_HEADER))
                .device(request.getHeader(DEVICE_HEADER))
                .userAction(audit.value())
                .createdAt(LocalDate.now())
                .build();
        log.info(auditDTO.toString());
        auditClient.sendAudit(jwtService.generateTokenCookie("security"), auditDTO);
    }

    @After("@annotation(forumAudit)")
    public void afterCompleteMarkOverflow(ForumAudit forumAudit) throws Exception {
        var sessionId = WebUtils.getCookie(request, SESSION_ID_COOKIE_NAME).getValue();

        AuditDTO auditDTO = AuditDTO.builder()
                .userLogin(sessionClient.getSessionById(sessionId).getLogin())
                .sessionId(sessionId)
                .ip(request.getHeader(IP_HEADER))
                .device(request.getHeader(DEVICE_HEADER))
                .userAction(forumAudit.value())
                .createdAt(LocalDate.now())
                .build();
        log.info(auditDTO.toString());
        auditClient.sendAudit(jwtService.generateTokenCookie("markoverflow"), auditDTO);
    }
}