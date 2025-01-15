package org.miracle.exception;

/**
 * Исключение, сообщающее об ошибке аунтефикации со стороны пользователя
 * @author Mark Valiev
 * @since 03.08.2024
 */
public class AuthorisationException extends UserException {

    public AuthorisationException() {
        super("Некорректный логин или пароль");
    }

}
