package cm.g2s.uaaservice.service;

import cm.g2s.uaaservice.shared.dto.PermissionDto;

public interface PermissionService {

    PermissionDto findById(String id);
    PermissionDto findByName(String name);
}
