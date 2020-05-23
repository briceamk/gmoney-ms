package cm.g2s.company.service;

import cm.g2s.company.domain.model.Company;
import cm.g2s.company.security.CustomPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {
    Company create(CustomPrincipal principal, Company company);

    void update(CustomPrincipal principal, Company company);

    Company findById(CustomPrincipal principal, String id);

    Company findByCode(String code);

    Page<Company> findAll(CustomPrincipal principal, String code, String name,
                          String email, String phoneNumber, String mobileNumber,
                          String vat, String trn, String street, String city, PageRequest pageRequest);

    void deleteById(CustomPrincipal principal, String id);

    Company dbStoreImage(CustomPrincipal principal, Company company, MultipartFile image);

    MultipartFile dbDefaultImage(CustomPrincipal principal, Company company);


}
