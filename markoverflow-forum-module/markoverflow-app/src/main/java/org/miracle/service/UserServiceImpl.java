package org.miracle.service;

import lombok.RequiredArgsConstructor;
import org.miracle.annotation.ForumAudit;
import org.miracle.model.User;
import org.miracle.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserService}
 *
 * @author Mark Valiev
 * @since 21.09.2024
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SessionCashService sessionService;
    private final UserRepository repository;

    @Override
    public User findUser() {
        var userLogin = sessionService.getSession().getLogin();
        var user = repository.findByLogin(userLogin);
        if (user.isEmpty()) {
            var newUser = User.builder().login(userLogin).build();
            repository.save(newUser);
            return newUser;
        }
        return user.get();
    }

    @ForumAudit("Удаление пользователя")
    @Override
    public void deleteUser(Long userId) {
        repository.deleteById(userId);
    }

}