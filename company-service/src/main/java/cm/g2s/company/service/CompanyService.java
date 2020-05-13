package cm.g2s.company.service;

import cm.g2s.company.shared.dto.CompanyDto;
import cm.g2s.company.shared.dto.CompanyDtoPage;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {
    CompanyDto create(CompanyDto companyDto);

    void update(CompanyDto companyDto);

    CompanyDto findById(String id);

    CompanyDto findByCode(String code);

    CompanyDtoPage findAll(String code, String name, String email, String phoneNumber, String mobileNumber, String vat, String trn, String street, String city, PageRequest of);

    void deleteById(String id);

    CompanyDto dbStoreImage(CompanyDto companyDto, MultipartFile image);

    MultipartFile dbDefaultImage(CompanyDto companyDto);


}
