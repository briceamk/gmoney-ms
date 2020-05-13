package cm.g2s.account.service.partner.service;

import cm.g2s.account.service.partner.model.PartnerDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("partnerClientService")
public class PartnerClientServiceImpl implements PartnerClientService {

    private final PartnerClientService partnerClientService;

    @Override
    @HystrixCommand(fallbackMethod = "findByPartnerIdFallBack",
            commandProperties = {@HystrixProperty(name = "execution.timeout.enabled", value = "false")})
    public PartnerDto findById(String id) {
        return partnerClientService.findById(id);
    }

    public PartnerDto findByPartnerIdFallBack(String id) {
        return new PartnerDto();
    }
}
