package org.miracle.service;

import org.miracle.dto.AuditDTO;

/**
 * Класс, создающий и созраняющий аудит
 * @author Mark Valiev
 * @since 12.09.2024
 */
public interface AuditService {

    /**
     * Метод, создающий сущность аудита и сохраняющий его в БД
     * @param auditDTO - Обьект передачи данных аудита
     */
    void createAudit(AuditDTO auditDTO);

}
