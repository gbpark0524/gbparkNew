package kr.pe.gbpark.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(kr.pe.gbpark.controller.ExceptionController)")
    public void exceptionHandler() {
    }
    
    @Pointcut("within(kr.pe.gbpark.controller.*)")
    public void controller() {
    }

    @Pointcut("within(kr.pe.gbpark.service.*)")
    public void service() {
    }

    @Before("controller() || service()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.debug("Enter: {}.{} with arguments = {}", className, methodName, Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning("controller() || service()")
    public void logAfter(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.debug("Exit: {}.{}", className, methodName);
    }

    @Before("exceptionHandler()")
    public void logException(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Exception exception = (Exception) args[0];
        log.error("Exception : {} ", exception.getMessage(), exception);
    }
}
