package cm.g2s.account;

import cm.g2s.account.infrastructure.broker.AccountEventSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableBinding(AccountEventSource.class)
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}


}
