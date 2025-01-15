package org.miracle.exception;

public class InvalidRootTokenException extends RuntimeException {

    public InvalidRootTokenException() {
        super("Токен недействителен или время жизни токена истекло");
    }
}
