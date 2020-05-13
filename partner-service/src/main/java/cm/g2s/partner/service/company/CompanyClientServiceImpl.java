package cm.g2s.partner.service.company;

import cm.g2s.partner.shared.dto.CompanyDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("companyClientService")
public class CompanyClientServiceImpl implements CompanyClientService {

    private final CompanyClientService companyClientService;

    @Override
    @HystrixCommand(fallbackMethod = "findByIdFallBack",
            commandProperties = {@HystrixProperty(name = "execution.timeout.enabled", value = "false")})
    public CompanyDto findById(String id) {
        return companyClientService.findById(id);
    }

    public CompanyDto findByIdFallBack(String id) {
        return new CompanyDto();
    }
}
