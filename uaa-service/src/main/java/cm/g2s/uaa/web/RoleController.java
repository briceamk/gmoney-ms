package cm.g2s.uaa.web;


import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.service.RoleService;
import cm.g2s.uaa.service.ValidationErrorService;
import cm.g2s.uaa.shared.dto.RoleDto;
import cm.g2s.uaa.shared.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Api(value = "Role", tags = "Role End Points")
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
        if (pageNumber == null || pageNumber < 0){
            pageNumber = UaaConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = UaaConstantType.DEFAULT_PAGE_SIZE;
        }
        return new ResponseEntity<>(roleService.findAll(name, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.findById(id));
    }


}
