package org.miracle.exception;

/**
 * Ошибка возникающая при попытке получить несуществующую\истёкшую сессию
 *
 * @author Kharitonov Andrei
 * @since 22.08.2024
 */
public class InvalidSessionException extends RuntimeException {

    public InvalidSessionException(String id) {
        super(String.format("Сессия с id: '%s' отсутствует или истекла", id));
    }
}
