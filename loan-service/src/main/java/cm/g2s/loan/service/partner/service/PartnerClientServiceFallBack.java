package cm.g2s.loan.service.partner.service;

import cm.g2s.loan.service.partner.model.PartnerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PartnerClientServiceFallBack implements PartnerClientService{
    @Override
    public PartnerDto findById(String id) {
        log.error("Error when call partner-service api from loan service...");
        return null;
    }
}
