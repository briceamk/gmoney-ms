package cm.g2s.partner.service.company.service;

import cm.g2s.partner.service.company.model.CompanyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class CompanyClientServiceFallBack implements CompanyClientService{
    @Override
    public CompanyDto findById(String id) {
        log.error("Error with company-service ");
        return new CompanyDto();
    }
}
