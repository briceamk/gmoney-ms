package cm.g2s.documentation.controller;

import cm.g2s.documentation.config.ServiceDefinitionsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceDefinitionController {

    private ServiceDefinitionsContext definitionContext;

    @Autowired
    public ServiceDefinitionController(ServiceDefinitionsContext definitionContext) {
        this.definitionContext = definitionContext;
    }

    @GetMapping("/service/{serviceName}")
    public String getServiceDefinition(@PathVariable String serviceName){
        return definitionContext.getSwaggerDefinition(serviceName);

    }
}
