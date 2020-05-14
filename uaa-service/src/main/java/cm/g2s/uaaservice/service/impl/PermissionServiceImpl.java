package cm.g2s.uaaservice.service.impl;

import cm.g2s.uaaservice.domain.model.Permission;
import cm.g2s.uaaservice.repository.PermissionRepository;
import cm.g2s.uaaservice.service.PermissionService;
import cm.g2s.uaaservice.shared.dto.PermissionDto;
import cm.g2s.uaaservice.shared.exception.ResourceNotFoundException;
import cm.g2s.uaaservice.shared.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionDto findById(String id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Permission with id {} ,ot found", id);
                    throw new ResourceNotFoundException(String.format("Permission with id %s ,ot found", id));
                }
        );
        return permissionMapper.map(permission);
    }

    @Override
    public PermissionDto findByName(String name) {
        Permission permission = permissionRepository.findByName(name).orElseThrow(
                () -> {
                    log.error("Permission with name {} ,ot found", name);
                    throw new ResourceNotFoundException(String.format("Permission with name %s ,ot found", name));
                }
        );
        return permissionMapper.map(permission);
    }
}
