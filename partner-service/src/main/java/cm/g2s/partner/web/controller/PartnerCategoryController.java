package cm.g2s.partner.web.controller;

import cm.g2s.partner.service.PartnerCategoryService;
import cm.g2s.partner.service.PartnerService;
import cm.g2s.partner.shared.dto.PartnerCategoryDto;
import cm.g2s.partner.shared.dto.PartnerDto;
import cm.g2s.partner.shared.mapper.PartnerMapper;
import cm.g2s.partner.shared.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/partner-categories")
@RequiredArgsConstructor
@Api(value = "Partner Category", tags = "Partner Category End Points")
public class PartnerCategoryController {

    private final PartnerCategoryService partnerCategoryService;

    @PostMapping
    public ResponseEntity<?> createPartner(@Validated @RequestBody PartnerCategoryDto categoryDto) {
        categoryDto = partnerCategoryService.createPartnerCategory(categoryDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/partner-categories/{id}")
                .buildAndExpand(categoryDto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "category saved successfully!"));
    }
}
