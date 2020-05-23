package cm.g2s.company.web;

import cm.g2s.company.constant.CompanyConstantType;
import cm.g2s.company.domain.model.Company;
import cm.g2s.company.security.CurrentPrincipal;
import cm.g2s.company.security.CustomPrincipal;
import cm.g2s.company.service.CompanyService;
import cm.g2s.company.service.ValidationErrorService;
import cm.g2s.company.web.dto.CompanyDto;
import cm.g2s.company.web.dto.CompanyDtoPage;
import cm.g2s.company.web.mapper.CompanyMapper;
import cm.g2s.company.web.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
@Api(value = "Company", tags = "Company End Points")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('CREATE_COMPANY'))")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody CompanyDto companyDto, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;

        Company company = companyService.create(principal, companyMapper.map(companyDto));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/companies/{id}")
                .buildAndExpand(company.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Company saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('UPDATE_COMPANY'))")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody CompanyDto companyDto, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;

        companyService.update(principal, companyMapper.map(companyDto));
        return new ResponseEntity<>(new ResponseApi(true, "Company updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('READ_COMPANY')) or (hasRole('ROLE_USER') and hasAuthority('READ_COMPANY'))")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(companyMapper.map(companyService.findById(principal, id)), HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    //@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('READ_COMPANY')) or (hasRole('ROLE_USER') and hasAuthority('READ_COMPANY'))")
    public ResponseEntity<?> findByCode(@PathVariable String code) {
        return new ResponseEntity<>(companyMapper.map(companyService.findByCode(code)), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('READ_COMPANY')) or (hasRole('ROLE_USER') and hasAuthority('READ_COMPANY'))")
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "code", required = false) String code,
                                                  @RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                  @RequestParam(value = "mobileNumber", required = false) String mobileNumber,
                                                  @RequestParam(value = "email", required = false) String email,
                                                  @RequestParam(value = "vat", required = false) String vat,
                                                  @RequestParam(value = "trn", required = false) String trn,
                                                  @RequestParam(value = "street", required = false) String street,
                                                  @RequestParam(value = "city", required = false) String city){


        if (pageNumber == null || pageNumber < 0){
            pageNumber = CompanyConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = CompanyConstantType.DEFAULT_PAGE_SIZE;
        }

        Page<Company> companyPage = companyService.findAll(principal, code,
                name, email, phoneNumber, mobileNumber, vat, trn, street,
                city, PageRequest.of(pageNumber, pageSize));

        CompanyDtoPage companyDtoPage = new CompanyDtoPage(
                companyPage.getContent().stream().map(companyMapper::map).collect(Collectors.toList()),
                PageRequest.of(companyPage.getPageable().getPageNumber(),
                        companyPage.getPageable().getPageSize()),
                companyPage.getTotalElements()
        );

        return new ResponseEntity<>( companyDtoPage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('DELETE_COMPANY'))")
    public ResponseEntity<?> deleteById(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String id) {
        companyService.deleteById(principal, id);
        return new ResponseEntity<>(new ResponseApi(true, "Company deleted successfully!"), HttpStatus.OK);
    }

    @PutMapping("/uploadLogo/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('UPDATE_COMPANY'))")
    public ResponseEntity<?> storeCompanyLogo(@CurrentPrincipal CustomPrincipal principal,
                                              @PathVariable String id, @RequestParam(name = "image", required = true) MultipartFile image) {
        companyService.update(principal, companyService.dbStoreImage(principal, companyService.findById(principal, id), image));
        return ResponseEntity.ok(new ResponseApi(true, "Company logo updated successfully"));
    }


    @GetMapping("/downloadLogo/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('READ_COMPANY'))  or (hasRole('ROLE_USER') and hasAuthority('READ_COMPANY'))")
    public ResponseEntity<?> getCompanyLogo(@CurrentPrincipal CustomPrincipal principal,
                                            @PathVariable String id) {
        Company company = companyService.findById(principal, id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(company.getLogoImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + company.getLogoFileName().replace(id + "_", "") + "\"")
                .body(new ByteArrayResource(company.getLogoImage()));
    }
}
