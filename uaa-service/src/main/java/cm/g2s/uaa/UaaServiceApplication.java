package cm.g2s.uaa;

import cm.g2s.uaa.infrastructure.broker.UaaEventSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableBinding(value = UaaEventSource.class)
public class UaaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}

}
