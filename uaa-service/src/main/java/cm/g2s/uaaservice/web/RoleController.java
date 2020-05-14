package cm.g2s.uaaservice.web;


import cm.g2s.uaaservice.service.RoleService;
import cm.g2s.uaaservice.service.ValidationErrorService;
import cm.g2s.uaaservice.shared.dto.RoleDto;
import cm.g2s.uaaservice.shared.dto.RoleDtoPage;
import cm.g2s.uaaservice.shared.payload.ResponseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final ValidationErrorService validationErrorService;


    @PutMapping
    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleDto roleDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        roleService.update(roleDto);
        return ResponseEntity.ok().body(new ResponseApi(true, "Role updated successfully!"));
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "name", required = false) String name) {
        return new ResponseEntity<>(roleService.findAll(name, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.findById(id));
    }


}
