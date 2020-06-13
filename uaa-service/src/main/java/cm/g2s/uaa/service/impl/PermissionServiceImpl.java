package cm.g2s.uaa.service.impl;

import cm.g2s.uaa.domain.model.Permission;
import cm.g2s.uaa.exception.ResourceNotFoundException;
import cm.g2s.uaa.infrastructure.repository.PermissionRepository;
import cm.g2s.uaa.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;


    @Override
    public Permission findById(String id) {
        return permissionRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Permission with id {} ,ot found", id);
                    throw new ResourceNotFoundException(String.format("Permission with id %s ,ot found", id));
                }
        );
    }

    @Override
    public Permission findByName(String name) {
        return permissionRepository.findByName(name).orElseThrow(
                () -> {
                    log.error("Permission with name {} ,ot found", name);
                    throw new ResourceNotFoundException(String.format("Permission with name %s ,ot found", name));
                }
        );
    }
}
