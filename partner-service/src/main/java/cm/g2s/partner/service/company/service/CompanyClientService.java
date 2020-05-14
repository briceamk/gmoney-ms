package cm.g2s.partner.service.company.service;

import cm.g2s.partner.service.company.model.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("company")
public interface CompanyClientService {
    @GetMapping("/api/v1/companies/{id}")
    CompanyDto findById(@PathVariable String id);
}
