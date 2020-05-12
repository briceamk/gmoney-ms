package cm.g2s.partner.web.controller;

import cm.g2s.partner.service.PartnerService;
import cm.g2s.partner.shared.payload.ResponseApi;
import cm.g2s.partner.shared.dto.PartnerDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/partners")
@RequiredArgsConstructor
@Api(value = "Partner", tags = "Partner End Points")
public class PartnerController {

    private final PartnerService partnerService;

    @PostMapping
    public ResponseEntity<?> createPartner(@Valid @RequestBody PartnerDto partnerDto) {
        partnerDto = partnerService.createPartner(partnerDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/partners/{id}")
                .buildAndExpand(partnerDto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "partner saved successfully!"));
    }
}
