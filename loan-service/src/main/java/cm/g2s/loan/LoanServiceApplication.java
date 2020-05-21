package cm.g2s.loan;

import cm.g2s.loan.infrastructure.broker.LoanEventSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableBinding(LoanEventSource.class)
public class LoanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanServiceApplication.class, args);
	}

}
