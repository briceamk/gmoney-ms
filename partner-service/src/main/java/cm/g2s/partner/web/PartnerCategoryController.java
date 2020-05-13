package cm.g2s.partner.web;

import cm.g2s.partner.PartnerConstantType;
import cm.g2s.partner.service.PartnerCategoryService;
import cm.g2s.partner.shared.dto.PartnerCategoryDto;
import cm.g2s.partner.shared.dto.PartnerCategoryDtoPage;
import cm.g2s.partner.shared.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/partner-categories")
@RequiredArgsConstructor
@Api(value = "Partner Category", tags = "Partner Category End Points")
public class PartnerCategoryController {

    private final PartnerCategoryService partnerCategoryService;

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody PartnerCategoryDto categoryDto) {
        categoryDto = partnerCategoryService.create(categoryDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/partner-categories/{id}")
                .buildAndExpand(categoryDto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "category saved successfully!"));
    }

    @PutMapping
    public ResponseEntity<?> update(@Validated @RequestBody PartnerCategoryDto categoryDto) {
        partnerCategoryService.update(categoryDto);
        return new ResponseEntity<>(new ResponseApi(true, "category updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return new ResponseEntity<>(partnerCategoryService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PartnerCategoryDtoPage> findAll(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                            @RequestParam(value = "name", required = false) String name){


        if (pageNumber == null || pageNumber < 0){
            pageNumber = PartnerConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = PartnerConstantType.DEFAULT_PAGE_SIZE;
        }

        return new ResponseEntity<>(partnerCategoryService.findAll(name, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        partnerCategoryService.deleteById(id);
        return new ResponseEntity<>(new ResponseApi(true, "category deleted successfully!"), HttpStatus.OK);
    }

}
