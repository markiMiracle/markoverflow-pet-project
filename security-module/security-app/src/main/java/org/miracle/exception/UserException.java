package org.miracle.exception;

/**
 * Исключение, возникающее при ошибках пользователя
 * @author Mark Valiev
 * @since 03.08.2024
 */
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

}
