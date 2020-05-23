package cm.g2s.uaa.web;


import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.model.Role;
import cm.g2s.uaa.service.RoleService;
import cm.g2s.uaa.service.ValidationErrorService;
import cm.g2s.uaa.web.dto.RoleDto;
import cm.g2s.uaa.web.dto.RoleDtoPage;
import cm.g2s.uaa.web.mapper.RoleMapper;
import cm.g2s.uaa.web.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Api(value = "Role", tags = "Role End Points")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final ValidationErrorService validationErrorService;


    @PutMapping
    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleDto roleDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        roleService.update(roleMapper.map(roleDto));
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
        Page<Role> rolePage = roleService.findAll(name, PageRequest.of(pageNumber, pageSize));
        RoleDtoPage roleDtoPage = new RoleDtoPage(
                rolePage.getContent().stream().map(roleMapper::map).collect(Collectors.toList()),
                PageRequest.of(rolePage.getPageable().getPageNumber(),
                        rolePage.getPageable().getPageSize()),
                rolePage.getTotalElements()
        );
        return new ResponseEntity<>(roleDtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleMapper.map(roleService.findById(id)));
    }


}
