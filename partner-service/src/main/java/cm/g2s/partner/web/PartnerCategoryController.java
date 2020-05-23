package cm.g2s.partner.web;

import cm.g2s.partner.constant.PartnerConstantType;
import cm.g2s.partner.domain.model.PartnerCategory;
import cm.g2s.partner.security.CurrentPrincipal;
import cm.g2s.partner.security.CustomPrincipal;
import cm.g2s.partner.service.PartnerCategoryService;
import cm.g2s.partner.service.ValidationErrorService;
import cm.g2s.partner.web.dto.PartnerCategoryDto;
import cm.g2s.partner.web.dto.PartnerCategoryDtoPage;
import cm.g2s.partner.web.mapper.PartnerCategoryMapper;
import cm.g2s.partner.web.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/partner-categories")
@RequiredArgsConstructor
@Api(value = "Partner Category", tags = "Partner Category End Points")
public class PartnerCategoryController {

    private final PartnerCategoryService categoryService;
    private final PartnerCategoryMapper categoryMapper;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('CREATE_PARTNER_CATEGORY')")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody PartnerCategoryDto categoryDto, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;

        PartnerCategory category = categoryService.create(principal, categoryMapper.map(categoryDto));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/partner-categories/{id}")
                .buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "category saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('UPDATE_PARTNER_CATEGORY')")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody PartnerCategoryDto categoryDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        categoryService.update(principal, categoryMapper.map(categoryDto));
        return new ResponseEntity<>(new ResponseApi(true, "category updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER') and hasAuthority('READ_PARTNER_CATEGORY')")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal, @PathVariable String id) {
        return new ResponseEntity<>(categoryMapper.map(categoryService.findById(principal, id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                            @RequestParam(value = "name", required = false) String name){


        if (pageNumber == null || pageNumber < 0){
            pageNumber = PartnerConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = PartnerConstantType.DEFAULT_PAGE_SIZE;

        }

        Page<PartnerCategory> categoryPage = categoryService.findAll(principal, name, PageRequest.of(pageNumber, pageSize));
        PartnerCategoryDtoPage categoryDtoPage = new PartnerCategoryDtoPage(
                categoryPage.getContent().stream().map(categoryMapper::map).collect(Collectors.toList()),
                PageRequest.of(categoryPage.getPageable().getPageNumber(),
                        categoryPage.getPageable().getPageSize()),
                categoryPage.getTotalElements()
        );
        return new ResponseEntity<>( categoryDtoPage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and hasAuthority('READ_DELETE_CATEGORY')")
    public ResponseEntity<?> deleteById(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String id) {
        categoryService.deleteById(principal, id);
        return new ResponseEntity<>(new ResponseApi(true, "category deleted successfully!"), HttpStatus.OK);
    }

}
