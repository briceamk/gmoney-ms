package cm.g2s.partner.service.uaa.service;


import cm.g2s.partner.service.uaa.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "uaa", fallback = UaaClientServiceFallBack.class)
public interface UaaClientService {
    @GetMapping("/uaa/api/v1/users/{id}")
    UserDto findById(@PathVariable String id);
}
