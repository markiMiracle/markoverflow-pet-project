package org.miracle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, помещаемая над методом, подключает механизм аудирования
 * @author Mark Valiev
 * @since 13.09.2024
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecurityAudit {

    /**
     * Метод для обозначения действия пользователя
     * @return - строка, действие пользователя
     */
    String value();
}