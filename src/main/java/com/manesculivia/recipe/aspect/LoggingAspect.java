package com.manesculivia.recipe.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("@annotation(com.manesculivia.recipe.aspect.Logging)")
    private void loggingPointcut() {
        // not needed
    }

    @Around("loggingPointcut()")
    public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String params = Arrays.stream(proceedingJoinPoint.getArgs())
                .map(Object::toString)
                .collect(joining(", "));
        String methodName = proceedingJoinPoint.getSignature().getName();
        log.info("==> Execution of: {}(" + params + ")", methodName);

        Object result = proceedingJoinPoint.proceed();

        log.info("<== Method {}() returned {}", methodName, result);

        return result;
    }

}
