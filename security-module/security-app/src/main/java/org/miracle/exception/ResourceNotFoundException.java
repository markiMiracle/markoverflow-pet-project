package org.miracle.exception;


/**
 * Исключение, возникающее при отсутсвии необходимой записи в Базе Данных
 * @author Mark Valiev
 * @since 03.08.2024
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
