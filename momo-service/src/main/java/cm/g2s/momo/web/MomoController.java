package cm.g2s.momo.web;

import cm.g2s.momo.security.CurrentPrincipal;
import cm.g2s.momo.security.CustomPrincipal;
import cm.g2s.momo.service.MomoService;
import cm.g2s.momo.service.ValidationErrorService;
import cm.g2s.momo.web.payload.ApiUserRequest;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/momos")
@Api(value = "MoMo", tags = "MoMo End Points")
public class MomoController {

    private final MomoService momoService;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    public ResponseEntity<?> createApiUser(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody ApiUserRequest apiUserRequest, BindingResult result)   {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        //TODO to implements
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/loans/{id}")
                .buildAndExpand("").toUri();
        return ResponseEntity.created(uri).body("");
    }

    @GetMapping("/api-users/{XReferenceId}")
    public ResponseEntity<?> createApiUser(@CurrentPrincipal CustomPrincipal principal,
                                          @PathVariable String XReferenceId) {
        return ResponseEntity.ok(momoService.getApiUser(XReferenceId));
    }



}
