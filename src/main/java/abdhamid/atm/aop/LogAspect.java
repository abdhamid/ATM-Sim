package abdhamid.atm.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Order(0)
@Aspect
@Configuration
public class LogAspect {

    @Around(value = "abdhamid.atm.aop.AppPointcuts.mainPointcut()")
    public Object executionTimeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        final Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        String methodArgs = Stream.of(joinPoint.getArgs()).toList().toString();
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        LoggerMessage message = LoggerMessage
                .builder()
                .className(className)
                .methodName(methodName)
                .methodArgs(methodArgs)
                .elapsedTimeinMillis(TimeUnit.NANOSECONDS.toMillis(elapsedTime))
                .build();

        logger.debug("LogAspect : {}", message);
        return result;
    }
}
