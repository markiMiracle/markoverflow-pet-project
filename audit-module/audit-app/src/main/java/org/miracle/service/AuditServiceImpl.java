package org.miracle.service;

import lombok.AllArgsConstructor;
import org.miracle.dto.AuditDTO;
import org.miracle.mapper.AuditMapper;
import org.miracle.repository.AuditRepository;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link AuditService}
 * @author Mark Valiev
 * @since 12.09.2024
 */
@Service
@AllArgsConstructor
public class AuditServiceImpl implements AuditService {

    private AuditRepository repository;
    private AuditMapper mapper;

    @Override
    public void createAudit(AuditDTO auditDTO) {
        repository.save(mapper.map(auditDTO));
    }
}

