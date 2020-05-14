package cm.g2s.uaaservice.service.impl;

import cm.g2s.uaaservice.domain.model.Role;
import cm.g2s.uaaservice.repository.RoleRepository;
import cm.g2s.uaaservice.service.PermissionService;
import cm.g2s.uaaservice.service.RoleService;
import cm.g2s.uaaservice.shared.dto.RoleDto;
import cm.g2s.uaaservice.shared.dto.RoleDtoPage;
import cm.g2s.uaaservice.shared.exception.ResourceNotFoundException;
import cm.g2s.uaaservice.shared.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Slf4j
@Service("roleService")
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;

    @Override
    public void update(RoleDto roleDto) {
        roleRepository.save(roleMapper.map(roleDto));
    }

    @Override
    public RoleDto findById(String id) {
        Role role =  roleRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Role with Id {} not found",id);
                    throw new ResourceNotFoundException(String.format("Role with Id %s not found", id));
                }
        );
        return roleMapper.map(role);
    }

    @Override
    public RoleDtoPage findAll(String name, PageRequest pageRequest) {
        Page<Role> rolePage;

        if (!StringUtils.isEmpty(name)) {
            //search by category name
            rolePage = roleRepository.findByName(name, pageRequest);
        }
        else{
            // search all
            rolePage = roleRepository.findAll(pageRequest);
        }

        return new RoleDtoPage(
                rolePage.getContent().stream().map(roleMapper::map).collect(Collectors.toList()),
                PageRequest.of(rolePage.getPageable().getPageNumber(),
                        rolePage.getPageable().getPageSize()),
                rolePage.getTotalElements()
        );
    }

    @Override
    public RoleDto findByName(String name) {
        Role role =  roleRepository.findByName(name).orElseThrow(
                () -> {
                    log.error("Role with name {} not found",name);
                    throw new ResourceNotFoundException(String.format("Role with name %s not found", name));
                }
        );
        return roleMapper.map(role);
    }
}
