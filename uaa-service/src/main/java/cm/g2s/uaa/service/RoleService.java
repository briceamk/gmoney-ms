package cm.g2s.uaa.service;

import cm.g2s.uaa.domain.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RoleService {
    void update(Role role);
    Role findById(String id);
    Page<Role> findAll(String name, PageRequest pageRequest);
    Role findByName(String name);
}
