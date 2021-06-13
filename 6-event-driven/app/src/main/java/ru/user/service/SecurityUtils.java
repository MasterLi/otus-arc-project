package ru.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public final class SecurityUtils {

    /**
     * получаем имя входа текущего пользователя
     *
     * @return login
     */
    public static String getLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(authentication) ? authentication.getName() : null;
    }

}
