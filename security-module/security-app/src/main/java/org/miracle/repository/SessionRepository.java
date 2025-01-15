package org.miracle.repository;

import org.miracle.model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс, отвечающий за сохранение сессии пользователя в Redis-хранилище
 * @author Mark Valiev
 * @since 02.08.2024
 */
@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
}
