package cm.g2s.company.service;

import cm.g2s.company.security.CustomPrincipal;
import cm.g2s.company.shared.dto.CompanyDto;
import cm.g2s.company.shared.dto.CompanyDtoPage;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {
    CompanyDto create(CustomPrincipal principal, CompanyDto companyDto);

    void update(CustomPrincipal principal, CompanyDto companyDto);

    CompanyDto findById(CustomPrincipal principal, String id);

    CompanyDto findByCode(String code);

    CompanyDtoPage findAll(CustomPrincipal principal, String code, String name, String email, String phoneNumber, String mobileNumber, String vat, String trn, String street, String city, PageRequest of);

    void deleteById(CustomPrincipal principal, String id);

    CompanyDto dbStoreImage(CustomPrincipal principal, CompanyDto companyDto, MultipartFile image);

    MultipartFile dbDefaultImage(CustomPrincipal principal, CompanyDto companyDto);


}
