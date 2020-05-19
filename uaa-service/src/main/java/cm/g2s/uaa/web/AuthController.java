package cm.g2s.uaa.web;


import cm.g2s.uaa.service.AuthService;
import cm.g2s.uaa.service.ValidationErrorService;
import cm.g2s.uaa.shared.payload.SignUp;
import cm.g2s.uaa.shared.dto.UserDto;
import cm.g2s.uaa.shared.payload.ResponseApi;
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

    private final AuthService authService;
    private final ValidationErrorService validationErrorService;



    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody SignUp signUp, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors!=null)
            return errors;

        UserDto userDto = authService.signUp(signUp);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/auth/sign-up/{id}")
                .buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(new ResponseApi(true, "We are processing your request. you will receive an email in few minutes!"));
    }
}
