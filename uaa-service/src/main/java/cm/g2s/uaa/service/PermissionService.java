package cm.g2s.uaa.service;

import cm.g2s.uaa.domain.model.Permission;

public interface PermissionService {

    Permission findById(String id);
    Permission findByName(String name);
}
