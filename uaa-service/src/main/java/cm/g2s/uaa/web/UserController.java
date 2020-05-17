package cm.g2s.uaa.web;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.security.commons.CurrentPrincipal;
import cm.g2s.uaa.service.UserService;
import cm.g2s.uaa.service.ValidationErrorService;
import cm.g2s.uaa.shared.dto.UserDto;
import cm.g2s.uaa.shared.payload.ResetPassword;
import cm.g2s.uaa.shared.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.nio.file.attribute.UserPrincipal;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Api(value = "User", tags = "User End Points")
public class UserController {

    private final UserService userService;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    public ResponseEntity<?> create(@CurrentPrincipal UserPrincipal userPrincipal,
                                    @Valid @RequestBody UserDto userDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        userDto = userService.create(userPrincipal, userDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/uaa/users/{id}")
                .buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "user saved successfully!"));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@CurrentPrincipal UserPrincipal userPrincipal,
                                        @Valid @RequestBody UserDto userDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        userService.update(userPrincipal, userDto);
        return ResponseEntity.ok().body(new ResponseApi(true, "user updated successfully!"));
    }

    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<?> resetPasswordUser(@CurrentPrincipal UserPrincipal userPrincipal,
                                               @PathVariable String userId, @Valid @RequestBody ResetPassword resetPassword,
                                        BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        userService.resetPassword(userPrincipal, userId, resetPassword);
        return ResponseEntity.ok().body(new ResponseApi(true, "password updated successfully!"));
    }

    @GetMapping
    public @ResponseBody ResponseEntity<?> findAll(@CurrentPrincipal UserPrincipal userPrincipal,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "mobile", required = false) String mobile) {
        if (pageNumber == null || pageNumber < 0){
            pageNumber = UaaConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = UaaConstantType.DEFAULT_PAGE_SIZE;
        }
        return ResponseEntity.ok(userService.findAll(userPrincipal, fullName, username, email, mobile, PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@CurrentPrincipal UserPrincipal userPrincipal,
                                         @PathVariable String id) {
        return ResponseEntity.ok(userService.findById(userPrincipal, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@CurrentPrincipal UserPrincipal userPrincipal,
                                        @PathVariable String id) {
        userService.deleteById(userPrincipal, id);
        return ResponseEntity.ok().body(new ResponseApi(true, "user deleted successfully!"));
    }



}
