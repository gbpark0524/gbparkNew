package kr.pe.gbpark.config;

import jakarta.servlet.http.HttpServletRequest;
import kr.pe.gbpark.util.common.IPUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {
    private final IPUtil ipUtil;

    @Pointcut("within(kr.pe.gbpark.controller.ExceptionController)")
    public void exceptionHandler() {
    }

    @Pointcut("within(kr.pe.gbpark.controller.*)")
    public void controller() {
    }

    @Pointcut("within(kr.pe.gbpark.service.*)")
    public void service() {
    }

    @After("controller()")
    public void logAfterController(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String ipAddress = ipUtil.getClientIpAddress(request);

        log.info("{} / {} with arguments = {}", ipAddress, requestURI, Arrays.toString(joinPoint.getArgs()));
    }

    @Before("exceptionHandler()")
    public void logException(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Exception exception = (Exception) args[0];
        log.error("Exception : {} ", exception.getMessage(), exception);
    }
}
