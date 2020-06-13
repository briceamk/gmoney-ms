package cm.g2s.uaa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket commandAPI(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cm.g2s.uaa.web"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }



    private ApiInfo getApiInfo(){
        return new ApiInfo(
                "UAA Service API",
                "Api to manage UAA",
                "1.0.0",
                "Terms of Service",
                new Contact("Brice MBIANDJI", "http://g2snet.com", "brice.mbiandji@g2snet.com"),
                "",
                "",
                Collections.emptyList());
    }

}
