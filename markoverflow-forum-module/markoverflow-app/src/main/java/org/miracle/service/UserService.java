package org.miracle.service;

import org.miracle.model.User;

/**
 * Класс, отвечающий за получение, создание и удаление сущности {@link User}
 *
 * @author Mark Valiev
 * @since 21.09.2024
 */
public interface UserService {

    User findUser();

    void deleteUser(Long userId);

}
