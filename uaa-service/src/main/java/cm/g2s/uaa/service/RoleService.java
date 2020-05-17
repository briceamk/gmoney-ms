package cm.g2s.uaa.service;

import cm.g2s.uaa.shared.dto.RoleDto;
import cm.g2s.uaa.shared.dto.RoleDtoPage;
import org.springframework.data.domain.PageRequest;

public interface RoleService {
    void update(RoleDto roleDto);
    RoleDto findById(String id);
    RoleDtoPage findAll(String name, PageRequest pageRequest);
    RoleDto findByName(String name);
}
