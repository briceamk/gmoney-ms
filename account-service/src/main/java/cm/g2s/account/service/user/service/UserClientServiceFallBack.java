package cm.g2s.account.service.user.service;

import cm.g2s.account.service.user.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserClientServiceFallBack implements UserClientService{
    @Override
    public UserDto findById(String id) {
        log.error("Error when calling uaa-service from account-service");
        return null;
    }
}
