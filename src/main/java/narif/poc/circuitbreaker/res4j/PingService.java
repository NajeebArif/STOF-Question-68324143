package narif.poc.circuitbreaker.res4j;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class PingService {

    public String ping(){
        return "ping";
    }

    @SneakyThrows
    public String sleep(int delay){
        Thread.sleep(delay*1000);
        return String.valueOf(delay);
    }
}
