package org.miracle.controller;

import lombok.AllArgsConstructor;
import org.miracle.dto.AuditDTO;
import org.miracle.service.AuditService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * Обработчик маршрутов для сохранения аудита
 * @author Mark Valiev
 * @since 12.09.2024
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuditController {

    private final AuditService service;

    @PostMapping("/audit")
    public void create(@Valid @RequestBody AuditDTO auditDTO) {
        service.createAudit(auditDTO);
    }

}
