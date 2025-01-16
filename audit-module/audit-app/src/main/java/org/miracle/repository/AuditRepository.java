package org.miracle.repository;

import org.miracle.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для сохранения сущности аудита в БД
 * @author Mark Valiev
 * @since 12.09.2024
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
}
