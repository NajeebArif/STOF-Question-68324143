package narif.poc.circuitbreaker.res4j;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PingController {

    private final PingService pingService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public PingController(PingService pingService, CircuitBreakerFactory circuitBreakerFactory) {
        this.pingService = pingService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @GetMapping
    public String ping(){
        return pingService.ping();
    }

    @GetMapping("/{delay}")
    public String callService(@PathVariable int delay){
        log.info("Calling the service method and sleeping for {} sec",delay);
        final CircuitBreaker delayBreaker = circuitBreakerFactory.create("delayBreaker");

        return delayBreaker.run(()->pingService.sleep(delay),throwable -> fallBack());
    }

    @GetMapping("/aspect/{delay}")
    @ApplyCircuitBreaker
    public String callServiceWithAspect(@PathVariable int delay){
        return pingService.sleep(delay);
    }

    public static String fallBack(){
        log.warn("Fallback Executed.");
        return "Fallback method executed.";
    }
}
