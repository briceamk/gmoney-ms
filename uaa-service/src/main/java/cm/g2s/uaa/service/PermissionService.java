package cm.g2s.uaa.service;

import cm.g2s.uaa.shared.dto.PermissionDto;

public interface PermissionService {

    PermissionDto findById(String id);
    PermissionDto findByName(String name);
}
