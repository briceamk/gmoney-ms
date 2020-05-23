package cm.g2s.uaa.service.impl;

import cm.g2s.uaa.domain.model.Role;
import cm.g2s.uaa.infrastructure.repository.RoleRepository;
import cm.g2s.uaa.service.RoleService;
import cm.g2s.uaa.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Slf4j
@Service("roleService")
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Role with Id {} not found",id);
                    throw new ResourceNotFoundException(String.format("Role with Id %s not found", id));
                }
        );
    }

    @Override
    public Page<Role>  findAll(String name, PageRequest pageRequest) {
        Page<Role> rolePage;

        if (!StringUtils.isEmpty(name)) {
            //search by name
            rolePage = roleRepository.findByName(name, pageRequest);
        }
        else{
            // search all
            rolePage = roleRepository.findAll(pageRequest);
        }

        return rolePage;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(
                () -> {
                    log.error("Role with name {} not found",name);
                    throw new ResourceNotFoundException(String.format("Role with name %s not found", name));
                }
        );
    }
}
