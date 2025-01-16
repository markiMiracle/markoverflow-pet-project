package org.miracle.client;

import org.miracle.dto.AuditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Клиент для передачи AuditDTO сервису аудита
 * @author Mark Valiev
 * @since 12.09.2024
 */
@FeignClient(name = "audit", url = "${audit.feign.client.url}")
public interface AuditClient {

    String TOKEN_ROOT_COOKIE_NAME = "ROOT_TOKEN";

    @RequestMapping(method = RequestMethod.POST, value = "/api/audit")
    void sendAudit(@CookieValue(TOKEN_ROOT_COOKIE_NAME) String tokenRootCookie, @RequestBody AuditDTO auditDTO);

}