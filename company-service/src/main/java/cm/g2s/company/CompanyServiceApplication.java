package cm.g2s.company;

import cm.g2s.company.configuration.FileStorageConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableConfigurationProperties({
		FileStorageConfiguration.class
})
public class CompanyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyServiceApplication.class, args);
	}

}
