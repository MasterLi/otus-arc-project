package ru.user.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.user.entity.User;
import ru.user.exception.ForbiddenException;
import ru.user.repository.UserRepository;
import ru.user.service.SecurityUtils;

import java.util.Arrays;


/**
 * проверка доступа пользователя на выполнение метода @AccessCheck
 * Create by Anton Arefyev
 * arefevayu@synergyteam.msk.ru
 */
@Slf4j
@Aspect
@Component
public final class AccessCheckAspect {
    private final UserRepository userRepository;

    public AccessCheckAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Around("execution(@ru.user.aspect.AccessCheck * *.*(..))")
    public Object methodAccessCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        AccessCheck accessCheck = AnnotationUtils.getAnnotation(joinPoint, AccessCheck.class);
        if (accessCheck != null) {
            String[] codes = accessCheck.value();
            String login = SecurityUtils.getLogin();
            User user = userRepository.findByUsernameLike(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (!Arrays.asList(codes).contains(user.getRole())) {
                throw new ForbiddenException("Private area. Check your role");
            }
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }
}