package org.miracle.repository;

import org.miracle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Интерфейс, отвечающий за сохранение пользователя в базу данных
 * @author  Mark Valiev
 * @since 02.08.2024
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

}
