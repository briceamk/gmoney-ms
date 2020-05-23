package cm.g2s.uaa.service.partner.service;

import cm.g2s.uaa.service.partner.dto.PartnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("partnerClientService")
public class PartnerClientServiceImpl implements PartnerClientService {

    private final PartnerClientService partnerClientService;


    @Override
    public PartnerDto findById(String id) {
        log.info("Calling company-service api from uaa-service.....");
        return partnerClientService.findById(id);
    }

}
