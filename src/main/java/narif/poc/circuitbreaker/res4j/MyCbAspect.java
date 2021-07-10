package narif.poc.circuitbreaker.res4j;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyCbAspect {


    private final CircuitBreakerFactory circuitBreakerFactory;

    public MyCbAspect(CircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Around("@annotation(narif.poc.circuitbreaker.res4j.ApplyCircuitBreaker)")
    public Object aroundAdvice(final ProceedingJoinPoint joinPoint)  {
        log.info("Aspect invoked.");
        final CircuitBreaker circuitBreaker = circuitBreakerFactory.create("test");
        return circuitBreaker.run(()-> proceed(joinPoint), throwable -> PingController.fallBack());
    }

    @SneakyThrows
    private Object proceed(ProceedingJoinPoint joinPoint)  {
        return joinPoint.proceed();
    }

}
