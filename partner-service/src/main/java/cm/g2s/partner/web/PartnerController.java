package cm.g2s.partner.web;

import cm.g2s.partner.constant.PartnerConstantType;
import cm.g2s.partner.service.PartnerService;
import cm.g2s.partner.service.ValidationErrorService;
import cm.g2s.partner.shared.dto.PartnerDto;
import cm.g2s.partner.shared.dto.PartnerDtoPage;
import cm.g2s.partner.shared.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/partners")
@RequiredArgsConstructor
@Api(value = "Partner", tags = "Partner End Points")
public class PartnerController {

    private final PartnerService partnerService;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER') and hasAuthority('CREATE_PARTNER')")
    public ResponseEntity<?> create(@Valid @RequestBody PartnerDto partnerDto, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;

        partnerDto = partnerService.create(partnerDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/partners/{id}")
                .buildAndExpand(partnerDto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Partner saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER') and hasAuthority('UPDATE_PARTNER')")
    public ResponseEntity<?> update(@Validated @RequestBody PartnerDto partnerDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        partnerService.update(partnerDto);
        return new ResponseEntity<>(new ResponseApi(true, "Partner updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER') and hasAuthority('READ_PARTNER')")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return new ResponseEntity<>(partnerService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER') and hasAuthority('READ_PARTNER')")
    public ResponseEntity<PartnerDtoPage> findAll(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "firstName", required = false) String firstName,
                                                  @RequestParam(value = "lastName", required = false) String lastName,
                                                  @RequestParam(value = "email", required = false) String email,
                                                  @RequestParam(value = "nicId", required = false) String nicId,
                                                  @RequestParam(value = "nicIssuePlace", required = false) String nicIssuePlace,
                                                  @RequestParam(value = "city", required = false) String city,
                                                  @RequestParam(value = "type", required = false) String type,
                                                  @RequestParam(value = "state", required = false) String state){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = PartnerConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = PartnerConstantType.DEFAULT_PAGE_SIZE;
        }

        return new ResponseEntity<>(partnerService.findAll(firstName, lastName, email, nicId, nicIssuePlace,
                city, type, state, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and hasAuthority('DELETE_PARTNER')")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        partnerService.deleteById(id);
        return new ResponseEntity<>(new ResponseApi(true, "Partner deleted successfully!"), HttpStatus.OK);
    }
}
