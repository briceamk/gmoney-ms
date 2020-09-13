package cm.g2s.transaction.momo.web;

import cm.g2s.transaction.momo.payload.*;
import cm.g2s.transaction.momo.service.MomoService;
import cm.g2s.transaction.momo.service.ValidationErrorService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createApiUser(@Valid @RequestBody ApiUserRequest apiUserRequest, BindingResult result)   {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        //TODO to implements
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/loans/{id}")
                .buildAndExpand("").toUri();
        return ResponseEntity.created(uri).body("");
    }

    @GetMapping("/api-user")
    public ResponseEntity<?> createApiUser() {
        return ResponseEntity.ok(momoService.getApiUser());
    }

    @PostMapping("/api-key")
    public ResponseEntity<?> generateApiKey()   {

        ApiKeyResponse apiKeyResponse = momoService.generateApiKey();
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/momos/api-key/{apiKeyResponse}")
                .buildAndExpand(apiKeyResponse.getApiKey()).toUri();
        return ResponseEntity.created(uri).body(apiKeyResponse);
    }

    @PostMapping("/token")
    public ResponseEntity<?> getAccessToken()   {
        return ResponseEntity.ok(momoService.getAccessToken());
    }

    @PostMapping("/transfer/{resourceId}")
    public ResponseEntity<?> makeTransfer(@RequestBody @Valid TransferRequest transferRequest, @PathVariable String resourceId, BindingResult result) throws JSONException {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        AccessTokenResponse accessTokenResponse = momoService.getAccessToken();
        Object response = momoService.makeTransfer(resourceId, transferRequest, accessTokenResponse);
        if(response instanceof Boolean && (Boolean)response) {
            return ResponseEntity.ok(true);
        }else if(response instanceof Reason) {
            if(((Reason) response).getStatus() == HttpStatus.CONFLICT.value()) {
                return new ResponseEntity<>((Reason)response, HttpStatus.CONFLICT);
            }
            else if(((Reason) response).getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ResponseEntity<>((Reason)response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else return new ResponseEntity<>((Reason)response, HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(false);
        }

    }

    @GetMapping("/transfer/{resourceId}")
    public ResponseEntity<?> findTransferInfo(@PathVariable String resourceId) throws JSONException {
        AccessTokenResponse accessTokenResponse = momoService.getAccessToken();
        Object response = momoService.findTransferInfo(resourceId, accessTokenResponse);
        if(response instanceof TransferResponse) {
            return ResponseEntity.ok((TransferResponse)response);
        }else if(response instanceof Reason) {
            if(((Reason) response).getStatus() == HttpStatus.CONFLICT.value()) {
                return new ResponseEntity<>((Reason)response, HttpStatus.CONFLICT);
            }
            else if(((Reason) response).getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ResponseEntity<>((Reason)response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else return new ResponseEntity<>((Reason)response, HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(false);
        }
    }



}
