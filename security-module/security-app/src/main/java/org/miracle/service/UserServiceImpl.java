package org.miracle.service;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.miracle.annotation.SecurityAudit;
import org.miracle.dto.UserDTO;
import org.miracle.exception.AuthorisationException;
import org.miracle.exception.RegistrationException;
import org.miracle.exception.ResourceNotFoundException;
import org.miracle.mapper.UserMapper;
import org.miracle.model.User;
import org.miracle.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Класс, реализующйи функционал интерфейса UserService
 * @author Mark Valiev
 * @since 03.08.2024
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    private UserMapper mapper;

    private SessionService sessionService;

    @SecurityAudit("Пользователь зарегестрирован")
    @Override
    public String createUser(UserDTO dto) {
        User user = mapper.map(dto);
        if (repository.findByLogin(user.getLogin()).isEmpty()) {
            repository.save(user);
            return sessionService.createSession(user.getLogin());
        } else {
            throw new RegistrationException(user.getLogin());
        }
    }

    @SecurityAudit("Пользователь авторизован")
    @Override
    public String login(UserDTO dto) {
        var user = repository.findByLogin(dto.getLogin()).orElseThrow(() -> new ResourceNotFoundException("Пользователя не существует"));

        if (BCrypt.checkpw(dto.getPassword(), user.getPasswordDigest())) {
            return sessionService.createSession(user.getLogin());
        } else {
            throw new AuthorisationException();
        }
    }

}
