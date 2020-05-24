package cm.g2s.account.service.partner.service;

import cm.g2s.account.service.partner.model.PartnerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PartnerClientServiceFallBack implements PartnerClientService{
    @Override
    public PartnerDto findById(String id) {
        log.error("Error when calling partner-service from account-service");
        return null;
    }
}
