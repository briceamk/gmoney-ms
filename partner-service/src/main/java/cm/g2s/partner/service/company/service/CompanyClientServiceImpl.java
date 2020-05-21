package cm.g2s.partner.service.company.service;

import cm.g2s.partner.service.company.model.CompanyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("companyClientService")
public class CompanyClientServiceImpl implements CompanyClientService {

    private final CompanyClientService companyClientService;

    @Override
    public CompanyDto findById(String id) {
        log.info("Calling company-service api from partner-service.....");
        return companyClientService.findById(id);
    }

}
