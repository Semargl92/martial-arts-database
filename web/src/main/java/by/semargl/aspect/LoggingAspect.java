package by.semargl.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger log = Logger.getLogger(LoggingAspect.class);

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void aroundServicePointcut() {
    }

    @Around("aroundServicePointcut()")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Service: Method " + joinPoint.getSignature().getName() + " start");
        StopWatch timer = new StopWatch();
        timer.start();
        Object proceed = joinPoint.proceed();
        timer.stop();
        log.info("Service: Method " + joinPoint.getSignature().getName() + " finished with time " + timer.getTotalTimeMillis() + " ms");
        return proceed;
    }

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    public void aroundRepositoryPointcut() {
    }

    @Around("aroundRepositoryPointcut()")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Repository: Method " + joinPoint.getSignature().getName() + " start");
        StopWatch timer = new StopWatch();
        timer.start();
        Object proceed = joinPoint.proceed();
        timer.stop();
        log.info("Repository: Method " + joinPoint.getSignature().getName() + " finished with time " + timer.getTotalTimeMillis() + " ms");
        return proceed;
    }
}
