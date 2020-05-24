package cm.g2s.account.service.user.service;

import cm.g2s.account.service.user.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "uaa", fallback = UserClientServiceFallBack.class)
public interface UserClientService {
    @GetMapping("/uaa/api/v1/users/{id}")
    UserDto findById(@PathVariable String id);
}
