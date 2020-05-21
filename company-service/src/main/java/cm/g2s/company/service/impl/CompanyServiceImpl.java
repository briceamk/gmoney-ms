package cm.g2s.company.service.impl;

import cm.g2s.company.configuration.FileStorageConfiguration;
import cm.g2s.company.constant.CompanyConstantType;
import cm.g2s.company.domain.model.Company;
import cm.g2s.company.repository.CompanyRepository;
import cm.g2s.company.security.CustomPrincipal;
import cm.g2s.company.service.CompanyService;
import cm.g2s.company.shared.dto.CompanyDto;
import cm.g2s.company.shared.dto.CompanyDtoPage;
import cm.g2s.company.shared.exception.ConflictException;
import cm.g2s.company.shared.exception.FileStorageException;
import cm.g2s.company.shared.exception.ResourceNotFoundException;
import cm.g2s.company.shared.mapper.CompanyMapper;
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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

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
    public CompanyDto create(CustomPrincipal principal,  CompanyDto companyDto) {
        // we check if code, name, phoneNumber, mobileNumber, email, vatNumber, tradeRegister exist
        if(companyRepository.existsByCode(companyDto.getCode())) {
            log.warn("Company with code {} exist!", companyDto.getCode());
            throw new ConflictException(String.format("Company with code %s exist!", companyDto.getCode()));
        }
        if(companyRepository.existsByName(companyDto.getName())) {
            log.warn("Company with name {} exist!", companyDto.getName());
            throw new ConflictException(String.format("Company with name %s exist!", companyDto.getName()));
        }
        if(companyDto.getPhoneNumber() != null && companyRepository.existsByPhoneNumber(companyDto.getPhoneNumber())) {
            log.warn("Company with phone number {} exist!", companyDto.getPhoneNumber());
            throw new ConflictException(String.format("Company with phone number %s exist!", companyDto.getPhoneNumber()));
        }
        if(companyDto.getMobileNumber() != null && companyRepository.existsByMobileNumber(companyDto.getMobileNumber())) {
            log.warn("Company with mobile number {} exist!", companyDto.getMobileNumber());
            throw new ConflictException(String.format("Company with mobile number %s exist!", companyDto.getMobileNumber()));
        }
        if(companyDto.getEmail() != null && companyRepository.existsByEmail(companyDto.getEmail())) {
            log.warn("Company with email {} exist!", companyDto.getEmail());
            throw new ConflictException(String.format("Company with email %s exist!", companyDto.getEmail()));
        }
        if(companyDto.getVat() != null && companyRepository.existsByVat(companyDto.getVat())) {
            log.warn("Company with vat number {} exist!", companyDto.getVat());
            throw new ConflictException(String.format("Company with vat number %s exist!", companyDto.getVat()));
        }
        if(companyDto.getTrn() != null && companyRepository.existsByTrn(companyDto.getTrn())) {
            log.warn("Company with trade register number {} exist!", companyDto.getTrn());
            throw new ConflictException(String.format("Company with vat trade register number %s exist!", companyDto.getTrn()));
        }
        companyDto = companyMapper.map(companyRepository.save(companyMapper.map(companyDto)));
        // we set and save default image
        MultipartFile image = dbDefaultImage(principal, companyDto);
        companyDto = dbStoreImage(principal, companyDto, image);
        return companyMapper.map(companyRepository.save(companyMapper.map(companyDto)));
    }

    @Override
    public void update(CustomPrincipal principal, CompanyDto companyDto) {
        // TODO validate unique fields
        //we check if logo is not in payload and set it before saving
        if(companyDto.getLogoImage() == null || companyDto.getLogoImage().length == 0)
            companyDto.setLogoImage(findById(principal, companyDto.getId()).getLogoImage());
        companyRepository.save(companyMapper.map(companyDto));
    }

    @Override
    public CompanyDto findById(CustomPrincipal principal, String id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("company with id %s not found!", id))
        );
        return companyMapper.map(company);
    }

    @Override
    public CompanyDto findByCode(String code) {
        Company company = companyRepository.findByCodeIgnoreCase(code).orElseThrow(
                () -> new ResourceNotFoundException(String.format("company with code %s not found!", code))
        );
        return companyMapper.map(company);
    }

    @Override
    public CompanyDtoPage findAll(CustomPrincipal principal, String code, String name, String email, String phoneNumber,
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

        return new CompanyDtoPage(
                companyPage.getContent().stream().map(companyMapper::map).collect(Collectors.toList()),
                PageRequest.of(companyPage.getPageable().getPageNumber(),
                        companyPage.getPageable().getPageSize()),
                companyPage.getTotalElements()
        );
    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("company with id %s not found!", id))
        );
        companyRepository.delete(company);
    }

    @Override
    public CompanyDto dbStoreImage(CustomPrincipal principal, CompanyDto companyDto, MultipartFile image) {
        // normalize file name
        String logoFileName = StringUtils.cleanPath(image.getOriginalFilename());
        try {
            // check if file name container invalid character
            if(logoFileName.contains("..")) {
                throw new FileStorageException("Sorry! image name contains invalid characters sequence");
            }
            companyDto.setLogoImage(image.getBytes());
            companyDto.setLogoFileName(companyDto.getId() + "_" + logoFileName );
            companyDto.setLogoImageType(image.getContentType());
            return companyDto;
        } catch (IOException ioe) {
            throw new FileStorageException("Could not store file " + logoFileName + ". Please try again!");
        }
    }

    @Override
    public MultipartFile dbDefaultImage(CustomPrincipal principal, CompanyDto companyDto) {
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
