package narif.poc.circuitbreaker.res4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Res4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(Res4jApplication.class, args);
	}

}
