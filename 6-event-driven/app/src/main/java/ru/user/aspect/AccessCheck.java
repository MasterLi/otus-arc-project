package ru.user.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * аннотация проверки доступа пользователя на выполнение метода
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessCheck {
    /**
     * Коды доступа к выполняемому методу
     */
    String[] value();
}
