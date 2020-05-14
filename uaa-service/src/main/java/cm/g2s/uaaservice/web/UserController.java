package cm.g2s.uaaservice.web;

import cm.g2s.uaaservice.service.UserService;
import cm.g2s.uaaservice.service.ValidationErrorService;
import cm.g2s.uaaservice.shared.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    /*private final UserService userService;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        user = userService.add(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/uaa/users/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "user saved successfully!"));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        userService.update(user);
        return ResponseEntity.ok().body(new ResponseApi(true, "user updated successfully!"));
    }

    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<?> resetPasswordUser(@PathVariable String userId, @Valid @RequestBody ResetPassword resetPassword,
                                        BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        userService.resetPassword(userId, resetPassword);
        return ResponseEntity.ok().body(new ResponseApi(true, "password updated successfully!"));
    }

    @GetMapping
    public @ResponseBody Page<User> getUserByPage(
            @RequestParam(name = "page", required = true, defaultValue = CommonConstantTypes.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = true, defaultValue = CommonConstantTypes.DEFAULT_PAGE_SIZE) Integer size) {
        return userService.getByPage(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok().body(new ResponseApi(true, "user deleted successfully!"));
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<?> deleteUser(@PathVariable List<String> ids) {
        userService.deleteAll(ids);
        return ResponseEntity.ok().body(new ResponseApi(true, "users deleted successfully!"));
    }*/


}
