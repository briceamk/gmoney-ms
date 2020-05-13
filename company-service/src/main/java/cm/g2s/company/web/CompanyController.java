package cm.g2s.company.web;

import cm.g2s.company.constant.CompanyConstantType;
import cm.g2s.company.service.CompanyService;
import cm.g2s.company.shared.dto.CompanyDto;
import cm.g2s.company.shared.dto.CompanyDtoPage;
import cm.g2s.company.shared.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Api(value = "Company", tags = "Company End Points")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody CompanyDto companyDto) {
        companyDto = companyService.create(companyDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/companies/{id}")
                .buildAndExpand(companyDto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Company saved successfully!"));
    }

    @PutMapping
    public ResponseEntity<?> update(@Validated @RequestBody CompanyDto companyDto) {
        companyService.update(companyDto);
        return new ResponseEntity<>(new ResponseApi(true, "Company updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return new ResponseEntity<>(companyService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code) {
        return new ResponseEntity<>(companyService.findByCode(code), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CompanyDtoPage> findAll(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
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

        return new ResponseEntity<>(companyService.findAll(code, name, email, phoneNumber, mobileNumber, vat, trn, street, city, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        companyService.deleteById(id);
        return new ResponseEntity<>(new ResponseApi(true, "Company deleted successfully!"), HttpStatus.OK);
    }

    @PutMapping("/uploadLogo/{id}")
    public ResponseEntity<?> storeCompanyLogo(@PathVariable String id, @RequestParam(name = "image", required = true) MultipartFile image) {
        CompanyDto companyDto = companyService.dbStoreImage(companyService.findById(id), image);
        companyService.update(companyDto);
        return ResponseEntity.ok(new ResponseApi(true, "Company logo updated successfully"));
    }


    @GetMapping("/downloadLogo/{id}")
    public ResponseEntity<?> getCompanyLogo(@PathVariable String id) {
        CompanyDto companyDto = companyService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(companyDto.getLogoImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + companyDto.getLogoFileName().replace(id + "_", "") + "\"")
                .body(new ByteArrayResource(companyDto.getLogoImage()));
    }
}
