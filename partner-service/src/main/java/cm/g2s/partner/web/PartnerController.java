package cm.g2s.partner.web;

import cm.g2s.partner.constant.PartnerConstantType;
import cm.g2s.partner.domain.model.Partner;
import cm.g2s.partner.security.CurrentPrincipal;
import cm.g2s.partner.security.CustomPrincipal;
import cm.g2s.partner.service.PartnerService;
import cm.g2s.partner.service.ValidationErrorService;
import cm.g2s.partner.web.dto.PartnerDto;
import cm.g2s.partner.web.dto.PartnerDtoPage;
import cm.g2s.partner.web.mapper.PartnerMapper;
import cm.g2s.partner.web.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/partners")
@RequiredArgsConstructor
@Api(value = "Partner", tags = "Partner End Points")
public class PartnerController {

    private final PartnerService partnerService;
    private final ValidationErrorService validationErrorService;
    private final PartnerMapper partnerMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER') and hasAuthority('CREATE_PARTNER')")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody PartnerDto partnerDto, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;

        Partner partner = partnerService.create(principal, partnerMapper.map(partnerDto));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/partners/{id}")
                .buildAndExpand(partner.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Partner saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER') and hasAuthority('UPDATE_PARTNER')")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody PartnerDto partnerDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        partnerService.update(principal, partnerMapper.map(partnerDto));
        return new ResponseEntity<>(new ResponseApi(true, "Partner updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER') and hasAuthority('READ_PARTNER')")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(partnerMapper.map(partnerService.findById(principal, id)), HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER') and hasAuthority('READ_PARTNER')")
    public ResponseEntity<?> findByUserId(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String userId) {
        return new ResponseEntity<>(partnerMapper.map(partnerService.findByUserId(principal, userId)), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER') and hasAuthority('READ_PARTNER')")
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
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

        Page<Partner> partnerPage = partnerService.findAll(principal, firstName, lastName, email, nicId, nicIssuePlace,
                city, type, state, PageRequest.of(pageNumber, pageSize));

        PartnerDtoPage partnerDtoPage = new PartnerDtoPage(
                partnerPage.getContent().stream().map(partnerMapper::map).collect(Collectors.toList()),
                PageRequest.of(partnerPage.getPageable().getPageNumber(),
                        partnerPage.getPageable().getPageSize()),
                partnerPage.getTotalElements()
        );
        return new ResponseEntity<>(partnerDtoPage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and hasAuthority('DELETE_PARTNER')")
    public ResponseEntity<?> deleteById(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String id) {
        partnerService.deleteById(principal, id);
        return new ResponseEntity<>(new ResponseApi(true, "Partner deleted successfully!"), HttpStatus.OK);
    }
}
