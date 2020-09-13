package cm.g2s.transaction;

import cm.g2s.transaction.infrastructure.broker.TransactionEventSource;
import cm.g2s.transaction.momo.configuration.MomoApiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EnableBinding(TransactionEventSource.class)
@EnableConfigurationProperties({MomoApiConfiguration.class})
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

	//TODO This is just for test momo service in transaction service. remove it after test is complete
	@Bean
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(5000);
		clientHttpRequestFactory.setConnectionRequestTimeout(5000);
		return new RestTemplate(clientHttpRequestFactory);
	}

}
