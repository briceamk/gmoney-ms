package cm.g2s.uaa.web;


import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.service.UserManagerService;
import cm.g2s.uaa.service.ValidationErrorService;
import cm.g2s.uaa.web.payload.ResponseApi;
import cm.g2s.uaa.web.payload.SignUp;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Api(value = "Auth", tags = "Auth End Points")
public class AuthController {

    private final UserManagerService userManagerService;
    private final ValidationErrorService validationErrorService;



    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody SignUp signUp, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors!=null)
            return errors;

        User user = userManagerService.createNewUser(null, signUp);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/auth/sign-up/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new ResponseApi(true, "We are processing your request. you will receive an email in few minutes!"));
    }
}
