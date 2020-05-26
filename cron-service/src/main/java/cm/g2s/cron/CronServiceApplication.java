package cm.g2s.cron;

import cm.g2s.cron.infratructure.broker.CronEventSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableEurekaClient
@SpringBootApplication
@EnableBinding(CronEventSource.class)
public class CronServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronServiceApplication.class, args);
	}

}
