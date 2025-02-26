package cm.g2s.partner.service.uaa.service;

import cm.g2s.partner.service.uaa.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UaaClientServiceFallBack implements UaaClientService {
    @Override
    public UserDto findById(String id) {
        log.error("Error when calling uaa-service api from partner-service");
        return null;
    }
}
