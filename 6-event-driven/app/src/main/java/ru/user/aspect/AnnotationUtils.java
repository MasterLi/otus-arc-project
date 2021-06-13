package ru.user.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;

public final class AnnotationUtils {

    public static <A extends Annotation> A getAnnotation(ProceedingJoinPoint joinPoint, Class<A> annotationClass)
            throws NoSuchMethodException {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        A annotation = signature.getMethod().getAnnotation(annotationClass);

        if (annotation == null) {
            Class<?> implClass = joinPoint.getTarget().getClass();
            annotation = implClass.getMethod(signature.getMethod().getName(), signature.getParameterTypes()).
                    getAnnotation(annotationClass);
        }

        return annotation;
    }

    public static <A extends Annotation> A getAnnotation(ProceedingJoinPoint joinPoint, Class<A> annotationClass, boolean isClass)
            throws NoSuchMethodException {

        A annotation = getAnnotation(joinPoint, annotationClass);
        return (annotation == null && isClass) ? joinPoint.getTarget().getClass().getAnnotation(annotationClass) : annotation;
    }
}
