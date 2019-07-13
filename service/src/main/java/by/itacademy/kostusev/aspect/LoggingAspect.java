package by.itacademy.kostusev.aspect;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Log4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(by.itacademy.kostusev.service.*)")
    public void addLoggingToService() {
    }

    @Pointcut("execution(public * by.itacademy.kostusev.service.*Service.*(..))")
    public void addLogging() {
    }

    @Around("addLoggingToService() && addLogging()")
    public Object addLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("!!__method " + joinPoint.getSignature().getName()
                + " with args " + Arrays.toString(joinPoint.getArgs())
                + " in Class " + joinPoint.getSignature().getDeclaringTypeName() + " started");
        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("!!__method " + joinPoint.getSignature().getName()
                    + " with args " + Arrays.toString(joinPoint.getArgs())
                    + " in Class " + joinPoint.getSignature().getDeclaringTypeName()
                    + " threw an Exception " + ex);
            throw ex;
        }

        log.info("!!__method " + joinPoint.getSignature().getName()
                + " with args " + Arrays.toString(joinPoint.getArgs())
                + " in Class " + joinPoint.getSignature().getDeclaringTypeName() + " successfully executed");

        return result;
    }
}
