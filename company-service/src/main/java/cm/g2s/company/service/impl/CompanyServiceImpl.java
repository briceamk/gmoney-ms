package cm.g2s.company.service.impl;

import cm.g2s.company.configuration.FileStorageConfiguration;
import cm.g2s.company.constant.CompanyConstantType;
import cm.g2s.company.domain.model.Company;
import cm.g2s.company.repository.CompanyRepository;
import cm.g2s.company.security.CustomPrincipal;
import cm.g2s.company.service.CompanyService;
import cm.g2s.company.exception.ConflictException;
import cm.g2s.company.exception.FileStorageException;
import cm.g2s.company.exception.ResourceNotFoundException;
import cm.g2s.company.util.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private  Path storagePath;

    @Autowired
    public void setPath(FileStorageConfiguration storageConfiguration) {
        this.storagePath = Paths.get(storageConfiguration.getStoreLocation())
                .toAbsolutePath()
                .normalize();
        try{
            Files.createDirectories(this.storagePath);
        }catch (Exception ex){
            throw new FileStorageException("We can't create directory where file will be stored");
        }
    }

    @Override
    public Company create(CustomPrincipal principal,  Company company) {
        // we check if code, name, phoneNumber, mobileNumber, email, vatNumber, tradeRegister exist
        if(companyRepository.existsByCode(company.getCode())) {
            log.warn("Company with code {} exist!", company.getCode());
            throw new ConflictException(String.format("Company with code %s exist!", company.getCode()));
        }
        if(companyRepository.existsByName(company.getName())) {
            log.warn("Company with name {} exist!", company.getName());
            throw new ConflictException(String.format("Company with name %s exist!", company.getName()));
        }
        if(company.getPhoneNumber() != null && companyRepository.existsByPhoneNumber(company.getPhoneNumber())) {
            log.warn("Company with phone number {} exist!", company.getPhoneNumber());
            throw new ConflictException(String.format("Company with phone number %s exist!", company.getPhoneNumber()));
        }
        if(company.getMobileNumber() != null && companyRepository.existsByMobileNumber(company.getMobileNumber())) {
            log.warn("Company with mobile number {} exist!", company.getMobileNumber());
            throw new ConflictException(String.format("Company with mobile number %s exist!", company.getMobileNumber()));
        }
        if(company.getEmail() != null && companyRepository.existsByEmail(company.getEmail())) {
            log.warn("Company with email {} exist!", company.getEmail());
            throw new ConflictException(String.format("Company with email %s exist!", company.getEmail()));
        }
        if(company.getVat() != null && companyRepository.existsByVat(company.getVat())) {
            log.warn("Company with vat number {} exist!", company.getVat());
            throw new ConflictException(String.format("Company with vat number %s exist!", company.getVat()));
        }
        if(company.getTrn() != null && companyRepository.existsByTrn(company.getTrn())) {
            log.warn("Company with trade register number {} exist!", company.getTrn());
            throw new ConflictException(String.format("Company with vat trade register number %s exist!", company.getTrn()));
        }
        company = companyRepository.save(company);
        // we set and save default image
        MultipartFile image = dbDefaultImage(principal, company);
        company = dbStoreImage(principal, company, image);
        return companyRepository.save(company);
    }

    @Override
    public void update(CustomPrincipal principal, Company company) {
        // TODO validate unique fields
        //we check if logo is not in payload and set it before saving
        if(company.getLogoImage() == null || company.getLogoImage().length == 0)
            company.setLogoImage(findById(principal, company.getId()).getLogoImage());
        companyRepository.save(company);
    }

    @Override
    public Company findById(CustomPrincipal principal, String id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("company with id %s not found!", id))
        );
    }

    @Override
    public Company findByCode(String code) {
        return companyRepository.findByCodeIgnoreCase(code).orElseThrow(
                () -> new ResourceNotFoundException(String.format("company with code %s not found!", code))
        );
    }

    @Override
    public Page<Company> findAll(CustomPrincipal principal, String code, String name, String email, String phoneNumber,
                                  String mobileNumber, String vat, String trn, String street, String city, PageRequest pageRequest) {

        Page<Company> companyPage;

        if(!StringUtils.isEmpty(code))
            companyPage = companyRepository.findByCodeContainsIgnoreCase(code, pageRequest);
        else if(!StringUtils.isEmpty(name))
            companyPage = companyRepository.findByNameContainsIgnoreCase(name, pageRequest);
        else if(!StringUtils.isEmpty(email))
            companyPage = companyRepository.findByEmailContainsIgnoreCase(email, pageRequest);
        else if(!StringUtils.isEmpty(phoneNumber))
            companyPage = companyRepository.findByPhoneNumberIgnoreCase(phoneNumber, pageRequest);
        else if(!StringUtils.isEmpty(mobileNumber))
            companyPage = companyRepository.findByMobileNumberIgnoreCase(mobileNumber, pageRequest);
        else if(!StringUtils.isEmpty(vat))
            companyPage = companyRepository.findByVatContainsIgnoreCase(vat, pageRequest);
        else if(!StringUtils.isEmpty(trn))
            companyPage = companyRepository.findByTrnContainsIgnoreCase(trn, pageRequest);
        else if(!StringUtils.isEmpty(city))
            companyPage = companyRepository.findByCityContainsIgnoreCase(city, pageRequest);
        else if(!StringUtils.isEmpty(street))
            companyPage = companyRepository.findByStreetContainsIgnoreCase(street, pageRequest);
        else
            companyPage = companyRepository.findAll(pageRequest);

        return companyPage;
    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        companyRepository.delete(findById(principal, id));
    }

    @Override
    public Company dbStoreImage(CustomPrincipal principal, Company company, MultipartFile image) {
        // normalize file name
        String logoFileName = StringUtils.cleanPath(image.getOriginalFilename());
        try {
            // check if file name container invalid character
            if(logoFileName.contains("..")) {
                throw new FileStorageException("Sorry! image name contains invalid characters sequence");
            }
            company.setLogoImage(image.getBytes());
            company.setLogoFileName(company.getId() + "_" + logoFileName );
            company.setLogoImageType(image.getContentType());
            return company;
        } catch (IOException ioe) {
            throw new FileStorageException("Could not store file " + logoFileName + ". Please try again!");
        }
    }

    @Override
    public MultipartFile dbDefaultImage(CustomPrincipal principal, Company company) {
        try {
            Path path = this.storagePath.resolve(CompanyConstantType.DEFAULT_COMPANY_LOGO_FILE_NAME).normalize();
            byte[] content = Files.readAllBytes(path);
            MultipartFile multipartFile = new CustomMultipartFile(CompanyConstantType.DEFAULT_COMPANY_LOGO_FILE_NAME,
                    CompanyConstantType.DEFAULT_COMPANY_LOGO_FILE_NAME, CompanyConstantType.DEFAULT_COMPANY_LOGO_IMAGE_TYPE, content);
            return multipartFile;
        } catch (IOException ioe) {
            throw new FileStorageException("can't find default workflow logo image!");
        }
    }
}
