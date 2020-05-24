package cm.g2s.uaa.service.company.service;



import cm.g2s.uaa.service.company.model.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "company", fallback = CompanyClientServiceFallBack.class)
public interface CompanyClientService {

    @GetMapping("/company/api/v1/companies/code/{code}")
    CompanyDto findByCode(@PathVariable String code);
}
