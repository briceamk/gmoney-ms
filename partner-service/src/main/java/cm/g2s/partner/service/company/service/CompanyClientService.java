package cm.g2s.partner.service.company.service;


import cm.g2s.partner.service.company.model.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "company", fallback = CompanyClientServiceFallBack.class)
public interface CompanyClientService {
    @GetMapping("/company/api/v1/companies/{id}")
    CompanyDto findById(@PathVariable String id);
}
