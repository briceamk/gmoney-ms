package cm.g2s.documentation.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipInputStream;

@Slf4j
@Component
public class ServiceDescriptionUpdater {


    private static final String DEFAULT_SWAGGER_URL="/v2/api-docs";
    private static final String KEY_SWAGGER_URL="swagger_url";


    private DiscoveryClient discoveryClient;
    private RestTemplate template;
    private ServiceDefinitionsContext definitionContext;


    @Autowired
    public ServiceDescriptionUpdater(DiscoveryClient discoveryClient, RestTemplate template,
                                     ServiceDefinitionsContext definitionContext){
        this.template = template;
        this.discoveryClient = discoveryClient;
        this.definitionContext = definitionContext;
    }




    @Scheduled(fixedDelayString= "${swagger.config.refreshRate}")
    public void refreshSwaggerConfigurations(){
        log.debug("Starting Service Definition Context refresh");

        discoveryClient.getServices().stream().forEach(serviceId -> {
            log.debug("Attempting service definition refresh for Service : {} ", serviceId);
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
            if(serviceInstances == null || serviceInstances.isEmpty()){ //Should not be the case kept for failsafe
                log.info("No instances available for service : {} ",serviceId);
            }else{
                ServiceInstance instance = serviceInstances.get(0);
                String swaggerURL =  getSwaggerURL( instance);
                Optional<Object> jsonData = getSwaggerDefinitionForAPI(serviceId, swaggerURL);

                if(jsonData.isPresent()){
                    String content = getJSON(serviceId, jsonData.get());
                    definitionContext.addServiceDefinition(serviceId, content);
                }else{
                    log.error("Skipping service id : {} Error : Could not get Swagger definition from API ",serviceId);
                }

                log.info("Service Definition Context Refreshed at :  {}", LocalDateTime.now());
            }
        });
    }

    private String getSwaggerURL( ServiceInstance instance){
        String swaggerURL = instance.getMetadata().get(KEY_SWAGGER_URL);
        return swaggerURL != null ?
                instance.getMetadata().containsKey("contextPath")?
                        instance.getUri() + instance.getMetadata().get("contextPath") +swaggerURL : instance.getUri()+ swaggerURL :
                instance.getMetadata().containsKey("contextPath")?
                            instance.getUri() + instance.getMetadata().get("contextPath") + DEFAULT_SWAGGER_URL : instance.getUri()+DEFAULT_SWAGGER_URL;
    }

    private Optional<Object> getSwaggerDefinitionForAPI(String serviceName, String url){
        log.debug("Accessing the SwaggerDefinition JSON for Service : {} : URL : {} ", serviceName, url);
        try{
            Object jsonData = template.getForObject(url, Object.class);
            return Optional.of(jsonData);
        }catch(RestClientException ex){
            log.error("Error while getting service definition for service : {} ", serviceName);
            return Optional.empty();
        }

    }

    public String getJSON(String serviceId, Object jsonData){
        try {
            return new ObjectMapper().writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            log.error("Error : {} ", e.getMessage());
            return "";
        }
    }
}
