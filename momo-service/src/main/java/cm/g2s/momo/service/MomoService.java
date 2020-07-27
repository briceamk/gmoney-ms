package cm.g2s.momo.service;

import cm.g2s.momo.web.payload.ApiUserResponse;

public interface MomoService {
    ApiUserResponse getApiUser(String xReferenceId);
}
