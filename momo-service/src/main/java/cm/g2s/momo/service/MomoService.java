package cm.g2s.momo.service;

import cm.g2s.momo.web.payload.*;
import org.codehaus.jettison.json.JSONException;

public interface MomoService {
    ApiUserResponse getApiUser();

    ApiKeyResponse generateApiKey();

    AccessTokenResponse getAccessToken();

    Object makeTransfer(String resourceId, TransferRequest transferRequest, AccessTokenResponse accessTokenResponse) throws JSONException;

    Object findTransferInfo(String resourceId, AccessTokenResponse accessTokenResponse) throws JSONException;
}
