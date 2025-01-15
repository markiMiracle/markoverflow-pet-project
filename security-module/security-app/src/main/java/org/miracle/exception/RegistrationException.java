package org.miracle.exception;


public class RegistrationException extends UserException {

    public RegistrationException(String existsLogin) {
        super("Пользователь с логином '" + existsLogin + "' уже существует");
    }

}
