package cm.g2s.uaa.service.partner.dto.service;

import cm.g2s.uaa.service.partner.dto.PartnerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PartnerClientServiceFallBack implements PartnerClientService {

    @Override
    public PartnerDto findById(String id) {
        log.error("Error when calling partner-service api from uaa-service");
        return new PartnerDto();
    }
}
