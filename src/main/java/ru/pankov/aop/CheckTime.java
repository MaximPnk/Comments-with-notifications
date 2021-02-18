package ru.pankov.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CheckTime {

    @Pointcut("execution(public * ru.pankov.service.CommentServiceImpl.addComment(..))")
    public void pointcut() {}

    @Around("pointcut()")
    public Object test(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        long exec = System.currentTimeMillis() - start;

        log.info("Method works " + exec + " ms");

        return object;
    }
}
